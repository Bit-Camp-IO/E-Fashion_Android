package com.bitio.ui.profile.location

import com.bitio.usercomponent.domain.model.Location

data class LocationUIState(
    val loading: Boolean = false,
    val id: String = "",
    val isPrimary: Boolean = false,
    val userLocation: UserLocation = UserLocation(),
    val message: String = ""
)

data class UserLocation(
    override val latitude: Double = 0.0,
    override val longitude: Double = 0.0,
) : Location