package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.entities.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    override val id: String,
    override val profileImage: String,
    override val username: String,
    override val phoneNumber: String,
    override val email: String,
    override val address: String
) : UserResponse
