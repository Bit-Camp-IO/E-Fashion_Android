package com.bitio.authcomponent.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val email: String, val password: String)
