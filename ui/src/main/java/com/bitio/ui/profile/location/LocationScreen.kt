package com.bitio.ui.profile.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.BuildConfig
import com.bitio.ui.R
import com.bitio.utils.TAG_APP
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.getViewModel
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(navController: NavController) {
    val context = LocalContext.current
    Places.initialize(context, BuildConfig.MAP_API_KEY)
    val viewModel = getViewModel<LocationViewModel>()
    val state by viewModel.uiState

    val geocoder = Geocoder(context)


    var cityName by remember {
        mutableStateOf("")
    }

    if (state.error.isNotEmpty()) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    LocationContent(
        lat = viewModel.latitudeAndLongitude.value.latitude,
        lng = viewModel.latitudeAndLongitude.value.longitude,
        onSelectedLocation = { latitude, longitude ->
            viewModel.updateLocationInfo(latitude, longitude)
        },
        onClick = {},
        cityName = cityName,
        onCityNameChange = { cityName = it },
        isLoading = state.loading,
        newLatLng = state.locationInfo,
        onSearchClick = {
            viewModel.searchLocationAndMoveCamera(cityName, geocoder)
        }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun LocationContent(
    lat: Double,
    lng: Double,
    cityName: String,
    onSelectedLocation: (Double, Double) -> Unit,
    onClick: () -> Unit,
    onSearchClick: () -> Unit,
    onCityNameChange: (String) -> Unit,
    isLoading: Boolean,
    newLatLng: LatLng,
) {

    val context = LocalContext.current

    val placesClient = Places.createClient(context)

    val sessionToken = AutocompleteSessionToken.newInstance()

    val request = FindAutocompletePredictionsRequest.builder()
        .setTypeFilter(TypeFilter.CITIES)
        .setSessionToken(sessionToken)
        .setQuery(cityName)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            for (prediction in response.autocompletePredictions) {
                val placeId = prediction.placeId
                val fullText = prediction.getFullText(null).toString()
                Log.d(TAG_APP, "LocationContent: $fullText")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG_APP, "LocationContent: $exception")
        }


    val cameraPositionState = rememberCameraPositionState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = true)
    }

    val scope = rememberCoroutineScope()

    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true,
            )
        )
    }

    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                uiSettings = uiSettings,
                onMapClick = {
                    onSelectedLocation(it.latitude, it.longitude)
                },
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = rememberMarkerState(position = LatLng(lat, lng)),
                    title = "Parking spot ($lat, $lng)",
                    snippet = "Long click to delete",
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED
                    ),
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                )
            }
            SearchLocationTextField(
                modifier = Modifier
                    .padding(top = 64.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.TopCenter),
                value = cityName,
                onValueChane = onCityNameChange,
                onSearchClick = {
                    onSearchClick()
                    scope.launch(Dispatchers.Main) {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                newLatLng,
                                15f
                            )
                        )
                    }
                }
            )
            ConfirmButton(
                modifier = Modifier
                    .padding(bottom = 64.dp, start = 64.dp, end = 64.dp)
                    .align(Alignment.BottomCenter),
                onClick = onClick,
                isLoading = isLoading,
                enabled = cityName.isNotEmpty() && !isLoading
            )
        }
    }
}

@Composable
private fun SearchLocationTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChane: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChane,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = "Search Location",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = Color.Gray
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
            }
        )
    )
}

@Composable
private fun ConfirmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = stringResource(id = R.string.confirm),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@SuppressLint("NewApi")
private suspend fun searchLocationAndMoveCamera(
    cityName: String,
    geocoder: Geocoder,
    cameraPositionState: CameraPositionState,
    context: Context
) {
    try {
        val addresses = geocoder.getFromLocationName(cityName, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val location = addresses[0]
                val latitude = location.latitude
                val longitude = location.longitude
                val newLatLng = LatLng(latitude, longitude)
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(newLatLng, 20f))
            } else {
                Toast.makeText(context, "Location not found for '$cityName'", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    } catch (e: IOException) {
        Toast.makeText(context, "Error performing geocoding: ${e.message}", Toast.LENGTH_SHORT)
            .show()
    }
}
