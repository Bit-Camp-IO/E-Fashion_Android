package com.bitio.authcomponent.data.local

import kotlinx.coroutines.flow.Flow

interface AuthDao {
    fun getRefreshToken(): Flow<String?>
    suspend fun updateRefreshToken(token: String)
    fun getAccessTokenStream(): Flow<String>
}