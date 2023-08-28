package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.entities.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    override val id: String,
    override val fullName: String,
    @SerialName("profile")
    override val profileImage: String,
    override val provider: String,
    override val isVerified: Boolean,
    override val address: String,
    override val settings: List<String>,
    override val addresses: List<Address>
) : User
