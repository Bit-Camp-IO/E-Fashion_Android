package com.bitio.authcomponent.domain.repository

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun refreshAccessToken()
    suspend fun updateRefreshToken()
    suspend fun getAccessToken(): String
}