package com.bitio.authcomponent.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun refreshAccessToken()
    suspend fun updateRefreshToken()
    suspend fun getAccessToken(): String
    fun getAccessTokenStream(): Flow<String>
    fun quickRetrieveAccessToken(): String?
    fun checkIfUserLoggedIn(): Flow<String?>
}