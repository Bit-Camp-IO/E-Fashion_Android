package com.bitio.authcomponent.data.repository

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.remote.request.LoginBody
import com.bitio.authcomponent.data.remote.request.RegisterBody
import com.bitio.authcomponent.domain.entities.AuthData
import com.bitio.authcomponent.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val api: AuthApi, private val dao: AuthDao) : AuthRepository {
    private var cashedAccessToken: String? = null
    private var cashedRefreshToken: String? = null


    //Register & Save Auth Data
    override suspend fun register(fullName: String, email: String, password: String) {
        val body = RegisterBody(email = email, fullName = fullName, password, password)
        val authData = api.register(body).data!!
        saveAuthDataHelper(authData)
    }

    //Login & Save Auth Data
    override suspend fun login(email: String, password: String) {
        val body = LoginBody(email, password)
        val authData = api.login(body).data!!
        saveAuthDataHelper(authData)

    }

    override suspend fun getAccessToken(): String {
        if (cashedAccessToken == null)
            refreshAccessToken()
        return cashedAccessToken!!
    }

    override fun getAccessTokenStream(): Flow<String> {
        return dao.getAccessTokenStream()
    }

    /*shall be invoked every <10 min due to the fact
     that the access token itself expires every 10 min*/

    override suspend fun refreshAccessToken() {
        if (cashedRefreshToken == null)
            cashedRefreshToken = dao.getRefreshToken()
        cashedAccessToken = api.refreshAccessToken(cashedRefreshToken!!).data!!.token
    }

    override suspend fun updateRefreshToken() {
        TODO("Not yet implemented")
    }

    private suspend fun saveAuthDataHelper(authData: AuthData) {
        cashedAccessToken = authData.accessToken
        cashedRefreshToken = authData.refreshToken
        dao.updateRefreshToken(cashedRefreshToken!!)

    }


}

