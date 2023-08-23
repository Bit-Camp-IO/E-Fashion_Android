package com.bitio.infrastructure.auth.remote

import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.remote.request.LoginBody
import com.bitio.authcomponent.data.remote.request.RegisterBody
import com.bitio.authcomponent.data.remote.response.AccessTokenResponse
import com.bitio.authcomponent.data.remote.response.AuthDataResponse
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiRetrofit : AuthApi {
    @POST("auth/register")
    override suspend fun register(@Body registerBody: RegisterBody): ResponseWrapper<AuthDataResponse>

    @POST("auth/login")
    override suspend fun login(@Body loginBody: LoginBody): ResponseWrapper<AuthDataResponse>

    @GET("auth/refresh")
    override suspend fun refreshAccessToken(@Header(value = "X-Refresh-Token") refreshToken: String): ResponseWrapper<AccessTokenResponse>
}