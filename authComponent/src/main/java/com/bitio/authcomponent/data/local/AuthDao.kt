package com.bitio.authcomponent.data.local

import kotlinx.coroutines.flow.Flow

interface AuthDao {

    suspend fun getRefreshToken(): String
    suspend fun updateRefreshToken(token: String)
    fun getAccessTokenStream(): Flow<String>
}