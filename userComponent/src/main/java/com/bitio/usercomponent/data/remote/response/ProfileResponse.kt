package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.model.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    override val id: String? = null,
    override val email: String? = null,
    override val fullName: String? = null,
    @SerialName("profile")
    override val profileImage: String? = null,
    override val provider: String? = null,
    override val isVerified: Boolean? = null,
    override val settings: SettingsResponse? = null,
    override val phoneNumber: String? = null
) : Profile

