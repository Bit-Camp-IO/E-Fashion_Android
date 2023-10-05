package com.bitio.ui.profile.location

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class LocationViewModel : ViewModel() {

    private val _uiState = mutableStateOf(LocationUIState())
    val uiState = _uiState

    private val _latitudeAndLongitude = mutableStateOf(LocationInfo())
    val latitudeAndLongitude = _latitudeAndLongitude

    fun updateLocationInfo(lat: Double, lon: Double) {
        _latitudeAndLongitude.value = LocationInfo(lat, lon)
    }

    @SuppressLint("NewApi", "SuspiciousIndentation")
    fun searchLocationAndMoveCamera(
        cityName: String,
        geocoder: Geocoder
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = LocationUIState(loading = true)
                val addresses = withContext(Dispatchers.IO){
                    geocoder.getFromLocationName(cityName, 1)
                }
                    if (addresses?.isNotEmpty() == true) {
                        val location = addresses[0]
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val newLatLng = LatLng(latitude, longitude)
                        _uiState.value = LocationUIState(
                            loading = false,
                            locationInfo = newLatLng
                        )
                    } else {
                        _uiState.value = LocationUIState(
                            loading = false,
                            error = "Location not found for '$cityName'"
                        )
                    }

            } catch (e: IOException) {
                _uiState.value = LocationUIState(
                    loading = false,
                    error = "Error performing geocoding: ${e.message}"
                )
            }
        }
    }

}