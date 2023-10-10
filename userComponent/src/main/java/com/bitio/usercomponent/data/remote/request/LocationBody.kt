package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationBody(
    override val latitude: Double,
    override val longitude: Double
) : Location
