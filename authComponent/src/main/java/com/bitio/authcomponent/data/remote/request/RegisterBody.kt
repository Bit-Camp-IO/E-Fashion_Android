package com.bitio.authcomponent.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    val email: String,
    val fullName: String,
    val password: String,
    val confirmPassword: String
)
