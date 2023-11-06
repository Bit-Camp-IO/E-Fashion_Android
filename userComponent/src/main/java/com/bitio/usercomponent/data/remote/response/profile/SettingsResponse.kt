package com.bitio.usercomponent.data.remote.response.profile

import com.bitio.usercomponent.domain.model.profile.Settings
import kotlinx.serialization.Serializable

@Serializable
data class SettingsResponse(
    override val language: String,
    override val isNotificationEnabled: Boolean
) : Settings
