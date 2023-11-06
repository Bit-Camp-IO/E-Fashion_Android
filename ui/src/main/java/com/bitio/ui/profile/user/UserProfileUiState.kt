package com.bitio.ui.profile.user

import com.bitio.usercomponent.domain.model.profile.Address
import com.bitio.usercomponent.domain.model.profile.Location
import com.bitio.usercomponent.domain.model.profile.Settings
import com.bitio.usercomponent.domain.model.profile.UserProfile

data class UserProfileUiState(
    val loading: Boolean = false,
    val profileUi: UserProfile = ProfileUi(),
    val errorMessage: String = ""
)


data class ProfileUi(
    override val fullName: String = "",
    override val profileImage: String = "",
    override val phoneNumber: String = "",
    override val email: String = "",
    override val id: String = "",
    override val address: Address = AddressUI(),
    override val provider: String = "",
    override val isVerified: Boolean = false,
    override val settings: Settings = SettingsUi(),
) : UserProfile

data class AddressUI(
    override val id: String = "",
    override val isPrimary: Boolean = false,
    override val location: Location = LocationUi()
) : Address

data class LocationUi(
    override val latitude: Double = 0.0,
    override val longitude: Double = 0.0
) : Location

data class SettingsUi(
    override val language: String = "",
    override val isNotificationEnabled: Boolean = false
) : Settings