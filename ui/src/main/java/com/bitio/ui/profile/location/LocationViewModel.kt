package com.bitio.ui.profile.location

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.usecase.user.AddUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.utils.TAG_APP
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class LocationViewModel(
    private val addUserLocationUseCase: AddUserLocationUseCase,
    private val getAddressesOfUseCase: GetAddressesOfUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(LocationUIState())
    val uiState = _uiState

    private val cities = mutableSetOf<String>()
    private val _addresses = mutableStateOf(cities)
    val addresses = _addresses

    init {
        viewModelScope.launch {
            when (val response = getAddressesOfUseCase()) {
                is ResponseStatus.Error -> {
                    Log.d(TAG_APP, "initial:${response.errorMessage} ")
                    _uiState.value = LocationUIState(
                        loading = false,
                        errorMessage = response.errorMessage
                    )
                }

                is ResponseStatus.Success -> {
                    Log.d(TAG_APP, "initial:${response.data?.location} ")
                    response.data?.let {
                        val userLocation = UserLocation(
                            latitude = it.location.latitude,
                            longitude = it.location.longitude
                        )
                        _uiState.value = LocationUIState(
                            loading = false,
                            locationInfo = userLocation
                        )
                    }
                }
            }
        }
    }

    fun updateLocationInfo(lat: Double, lon: Double) {
        _uiState.value = LocationUIState(
            loading = false,
            locationInfo = UserLocation(lat, lon)
        )
    }

    fun findAutoPlace(
        query: String,
        placesClient: PlacesClient
    ) {
        val sessionToken = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypesFilter(listOf(PlaceTypes.CITIES))
            .setSessionToken(sessionToken)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    val placeId = prediction.placeId
                    val fullText = prediction.getFullText(null).toString()
                    cities.add(fullText)
                    _addresses.value = cities
                }
            }
            .addOnFailureListener { exception ->
                _uiState.value = LocationUIState(
                    loading = false,
                    errorMessage = exception.message.toString()
                )
            }
    }

    @SuppressLint("NewApi", "SuspiciousIndentation")
    fun searchLocationAndMoveCamera(
        cityName: String,
        geocoder: Geocoder
    ) {
        cities.clear()
        _addresses.value = cities
        viewModelScope.launch {
            try {
                _uiState.value = LocationUIState(loading = true)
                val addresses = withContext(Dispatchers.IO) {
                    geocoder.getFromLocationName(cityName, 1)
                }
                if (addresses?.isNotEmpty() == true) {
                    val location = addresses[0]
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val newLatLng = UserLocation(latitude, longitude)
                    _uiState.value = LocationUIState(
                        loading = false,
                        locationInfo = newLatLng
                    )
                } else {
                    _uiState.value = LocationUIState(
                        loading = false,
                        errorMessage = "Location not found for '$cityName'"
                    )
                }

            } catch (e: IOException) {
                _uiState.value = LocationUIState(
                    loading = false,
                    errorMessage = "Error performing geocoding: ${e.message}"
                )
            }
        }
    }

    fun confirmLocation() {
        viewModelScope.launch {
            _uiState.value = LocationUIState(loading = true)
            when (val response = addUserLocationUseCase( _uiState.value.locationInfo)) {
                is ResponseStatus.Error -> {
                    _uiState.value = LocationUIState(
                        loading = false,
                        errorMessage = response.errorMessage
                    )
                }

                is ResponseStatus.Success -> {
                    _uiState.value = LocationUIState(
                        loading = false,
                        locationInfo =  _uiState.value.locationInfo
                    )
                }
            }
        }
    }

}