package com.bitio.authcomponent.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun refreshAccessToken()
    suspend fun updateRefreshToken()
    suspend fun getAccessToken(): String
    fun getAccessTokenStream(): Flow<String>
    fun quickRetrieveAccessToken(): String?
    fun isUserLoggedIn(): StateFlow<Boolean>
}