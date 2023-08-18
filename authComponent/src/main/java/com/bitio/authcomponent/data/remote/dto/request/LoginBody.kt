package com.bitio.authcomponent.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody( val email: String, val password: String,)
