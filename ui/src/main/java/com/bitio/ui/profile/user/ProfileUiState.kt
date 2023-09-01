package com.bitio.ui.profile.user

data class ProfileUiState(
    val loading: Boolean = false,
    val profileUi: ProfileUi = ProfileUi(),
    val errorMessage: String = ""
)


data class ProfileUi(
    val fullName: String = "",
    val profileImage: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isVerified: Boolean = false,
    val settingsUi: SettingsUi = SettingsUi()
)

data class SettingsUi(
    val language: String = "",
    val addresses: List<AddressUi> = emptyList()
)

data class AddressUi(
    val id: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: Long = 0,
    val isPrimary: Boolean = false,
)