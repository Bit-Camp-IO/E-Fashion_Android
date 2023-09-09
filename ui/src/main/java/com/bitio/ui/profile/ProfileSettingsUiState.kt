package com.bitio.ui.profile

import com.bitio.usercomponent.domain.model.ProfileSettings

data class ProfileSettingsUiState(
    override var darkModeEnabled: Boolean = false,
    override var language: String = ""
) : ProfileSettings
