package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserBody(
    override val email: String?,
    override val fullName: String?,
    override val phoneNumber: String?
) : User