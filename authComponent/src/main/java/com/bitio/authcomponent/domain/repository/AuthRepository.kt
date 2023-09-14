package com.bitio.authcomponent.domain.repository

import com.bitio.authcomponent.data.remote.response.AuthDataResponse
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): ResponseWrapper<AuthDataResponse>

    suspend fun login(email: String, password: String): ResponseWrapper<AuthDataResponse>
    suspend fun refreshAccessToken()
    suspend fun updateRefreshToken(refreshToken: String)
    suspend fun getAccessToken(): String?
    fun getAccessTokenStream(): Flow<String?>
    fun quickRetrieveAccessToken(): String?
    fun checkIfUserLoggedIn(): Flow<String?>
}