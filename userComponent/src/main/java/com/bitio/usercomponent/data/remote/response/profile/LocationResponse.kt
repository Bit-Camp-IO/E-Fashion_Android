package com.bitio.usercomponent.data.remote.response.profile

import com.bitio.usercomponent.domain.model.profile.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    override val latitude: Double,
    override val longitude: Double
) : Location
