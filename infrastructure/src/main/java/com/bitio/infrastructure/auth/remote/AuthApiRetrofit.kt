package com.bitio.infrastructure.auth.remote

import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.remote.request.LoginBody
import com.bitio.authcomponent.data.remote.request.RegisterBody
import com.bitio.authcomponent.data.remote.request.ResetPasswordBody
import com.bitio.authcomponent.data.remote.response.AccessTokenResponse
import com.bitio.authcomponent.data.remote.response.AuthDataResponse
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiRetrofit : AuthApi {
    @POST("/api/auth/register")
    override suspend fun register(@Body registerBody: RegisterBody): ResponseWrapper<AuthDataResponse>

    @POST("/api/auth/login")
    override suspend fun login(@Body loginBody: LoginBody): ResponseWrapper<AuthDataResponse>

    @GET("/api/auth/refresh")
    override suspend fun refreshAccessToken(@Header(value = "X-Refresh-Token") refreshToken: String): ResponseWrapper<AccessTokenResponse>

    @GET("/api/auth/forgot-password")
    override suspend fun forgotPassword(email: String): ResponseWrapper<AuthDataResponse>

    @GET("/api/auth/forgot-password")
    override suspend fun verifyEmail(otp: String): ResponseWrapper<AuthDataResponse>

    @POST("/api/auth/reset-password")
    override suspend fun resetPassword(@Body resetPasswordBody: ResetPasswordBody): ResponseWrapper<AuthDataResponse>
}