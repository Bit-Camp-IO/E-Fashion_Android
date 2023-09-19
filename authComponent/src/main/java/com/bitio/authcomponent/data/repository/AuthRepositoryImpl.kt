package com.bitio.authcomponent.data.repository

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.remote.request.LoginBody
import com.bitio.authcomponent.data.remote.request.RegisterBody
import com.bitio.authcomponent.data.remote.response.AuthDataResponse
import com.bitio.authcomponent.domain.entities.AuthData
import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val dao: AuthDao
) : AuthRepository {

    //Register & Save Auth Data
    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): ResponseWrapper<AuthDataResponse> {
        val body = RegisterBody(email = email, fullName = fullName, password, password)
        val authData = api.register(body).data!!
        saveAuthDataHelper(authData)
        return api.register(body)
    }

    //Login & Save Auth Data
    override suspend fun login(email: String, password: String): ResponseWrapper<AuthDataResponse> {
        val body = LoginBody(email, password)
        val authData = api.login(body).data!!
        saveAuthDataHelper(authData)
        return api.login(body)
    }

    override suspend fun getAccessToken(): String? {
        if (cashedAccessToken == null) {
            refreshAccessToken()
        }
        return cashedAccessToken
    }

    override fun quickRetrieveAccessToken(): String? {
        return cashedAccessToken
    }

    override fun checkIfUserLoggedIn(): Flow<String?> {
        return dao.getRefreshToken()
    }


    override fun getAccessTokenStream(): Flow<String?> {
        return dao.getAccessTokenStream()
    }

    /*shall be invoked every <10 min due to the fact
     that the access token itself expires every 10 min*/

    override suspend fun refreshAccessToken() {
        if (cashedRefreshToken == null) {
            dao.getRefreshToken().collect {
                cashedRefreshToken = it
            }
        }
        cashedRefreshToken?.let {
            cashedAccessToken = api.refreshAccessToken(it).data?.token
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        dao.updateRefreshToken(refreshToken)
    }

    private suspend fun saveAuthDataHelper(authData: AuthData) {
        cashedAccessToken = authData.accessToken
        cashedRefreshToken = authData.refreshToken
        dao.updateRefreshToken(cashedRefreshToken!!)
    }

    companion object {
        private var cashedAccessToken: String? = null
        private var cashedRefreshToken: String? = null
    }

}

