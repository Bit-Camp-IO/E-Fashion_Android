package com.bitio.usercomponent.data.remote.request

import kotlinx.serialization.Serializable


@Serializable
data class UserBody(
    val id: String,
    val profileImage: String,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val address: String
)