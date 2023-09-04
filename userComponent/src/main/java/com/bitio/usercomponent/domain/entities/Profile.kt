package com.bitio.usercomponent.domain.entities

interface Profile {
    val id: String?
    val email: String?
    val fullName: String?
    val phoneNumber: String?
    val profileImage: String?
    val provider: String?
    val isVerified:Boolean?
    val settings: Settings?
}
