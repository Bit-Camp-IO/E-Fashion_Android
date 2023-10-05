package com.bitio.ui.profile.location

import com.google.android.gms.maps.model.LatLng

data class LocationUIState(
    val loading: Boolean = false,
    val locationInfo: LatLng = LatLng(0.0,0.0),
    val error: String = ""
)

data class LocationInfo(
    val latitude:Double = 0.0,
    val longitude:Double = 0.0,
)