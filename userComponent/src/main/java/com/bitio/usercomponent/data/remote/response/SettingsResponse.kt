package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.model.Settings
import kotlinx.serialization.Serializable

@Serializable
data class SettingsResponse(
    override val language: String? = null,
    override val addresses: List<Address>? = null
) : Settings
