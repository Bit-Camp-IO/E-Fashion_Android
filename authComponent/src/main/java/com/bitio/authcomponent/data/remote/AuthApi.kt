package com.bitio.authcomponent.data.remote

import com.bitio.authcomponent.data.remote.request.LoginBody
import com.bitio.authcomponent.data.remote.request.RegisterBody
import com.bitio.authcomponent.data.remote.request.ResetPasswordBody
import com.bitio.authcomponent.data.remote.response.AccessTokenResponse
import com.bitio.authcomponent.data.remote.response.AuthDataResponse
import com.bitio.sharedcomponent.data.ResponseWrapper


interface AuthApi {
    suspend fun register(registerBody: RegisterBody): ResponseWrapper<AuthDataResponse>
    suspend fun login(loginBody: LoginBody): ResponseWrapper<AuthDataResponse>
    suspend fun refreshAccessToken(refreshToken: String): ResponseWrapper<AccessTokenResponse>
    suspend fun forgotPassword(email: String): ResponseWrapper<AuthDataResponse>
    suspend fun verifyEmail(otp: String): ResponseWrapper<AuthDataResponse>
    suspend fun resetPassword(resetPasswordBody: ResetPasswordBody): ResponseWrapper<AuthDataResponse>
}



