package com.bitio.authcomponent.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordBody(
    val email: String,
    val newPassword: String,
    val otp: String
)
