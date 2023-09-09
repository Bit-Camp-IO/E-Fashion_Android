package com.bitio.infrastructure.user.local.data_store

import com.bitio.usercomponent.domain.model.ProfileSettings

internal data class ProfileSettingsEntity(
    override var darkModeEnabled: Boolean,
    override var language: String
):ProfileSettings
