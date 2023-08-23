package com.bitio.authcomponent.data.remote.response

import com.bitio.authcomponent.domain.entities.AuthData
import kotlinx.serialization.Serializable


@Serializable
data class AuthDataResponse(
    override val fullName: String,
    override val email: String,
    override val accessToken: String,
    override val refreshToken: String
) : AuthData
