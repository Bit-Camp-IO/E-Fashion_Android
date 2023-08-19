package com.bitio.authcomponent.data.local

interface AuthDao {

    suspend fun getRefreshToken(): String
    suspend fun updateRefreshToken(token: String)

}