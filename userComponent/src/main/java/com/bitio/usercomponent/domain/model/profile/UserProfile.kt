package com.bitio.usercomponent.domain.model.profile

interface UserProfile {
    val id: String
    val email: String
    val fullName: String
    val phoneNumber: String
    val profileImage: String
    val provider: String
    val isVerified: Boolean
    val address: Address
    val settings: Settings
}