package com.bitio.usercomponent.data.remote.response.profile

import com.bitio.usercomponent.domain.model.profile.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    override val id: String,
    override val email: String,
    override val fullName: String,
    @SerialName("profile")
    override val profileImage: String,
    override val provider: String,
    override val isVerified: Boolean,
    override val settings: SettingsResponse,
    override val phoneNumber: String,
    override val address: AddressResponse
) : UserProfile


