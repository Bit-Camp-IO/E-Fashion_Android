package com.bitio.usercomponent.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileBody(
    val email: String,
    val fullName: String,
    val phoneNumber: String,
)