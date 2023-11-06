package com.bitio.infrastructure.user.local.data_store

import com.bitio.usercomponent.domain.model.profile.LocalProfileSettings

internal data class LocalProfileSettingsEntity(
    override var darkModeEnabled: Boolean,
    override var language: String
): LocalProfileSettings
