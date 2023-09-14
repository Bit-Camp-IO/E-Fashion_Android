package com.bitio.authcomponent.data.remote.response

import com.bitio.authcomponent.domain.entities.AccessToken
import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName(value = "accessToken")
    override val token: String
) : AccessToken