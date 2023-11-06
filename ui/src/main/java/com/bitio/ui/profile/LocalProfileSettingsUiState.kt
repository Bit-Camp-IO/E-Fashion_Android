package com.bitio.ui.profile

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings

data class LocalProfileSettingsUiState(
    override var darkModeEnabled: Boolean = false,
    override var language: String = ""
) : LocalProfileSettings
