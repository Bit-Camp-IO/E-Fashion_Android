package com.bitio.authcomponent.data.remote

import com.bitio.authcomponent.data.remote.dto.request.LoginBody
import com.bitio.authcomponent.data.remote.dto.request.RegisterBody
import com.bitio.authcomponent.data.remote.dto.response.AccessTokenResponse
import com.bitio.authcomponent.data.remote.dto.response.AuthDataResponse
import com.bitio.authcomponent.data.remote.dto.response.ResponseWrapper


interface AuthApi {
    suspend fun register(registerBody: RegisterBody): ResponseWrapper<AuthDataResponse>
    suspend fun login(loginBody: LoginBody): ResponseWrapper<AuthDataResponse>
    suspend fun refreshAccessToken(refreshToken: String): ResponseWrapper<AccessTokenResponse>
}



