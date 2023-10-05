package com.bitio.ui.profile.location

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = getViewModel<LocationViewModel>()
    val state by viewModel.uiState
    var cityName by remember {
        mutableStateOf("")
    }


    val geocoder = Geocoder(context)

    if (state.error.isNotEmpty()) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    Places.initialize(context, BuildConfig.MAP_API_KEY)
    val placesClient = Places.createClient(context)

    LocationContent(
        lat = viewModel.latitudeAndLongitude.value.latitude,
        lng = viewModel.latitudeAndLongitude.value.longitude,
        onSelectedLocation = { latitude, longitude ->
            viewModel.updateLocationInfo(latitude, longitude)
        },
        onClick = {},
        cityName = cityName,
        onCityNameChange = {
            cityName = it
            viewModel.findAutoPlace(cityName, placesClient)
        },
        isLoading = state.loading,
        newLatLng = state.locationInfo,
        onSearchClick = {
            viewModel.searchLocationAndMoveCamera(cityName, geocoder)
        },
        suggestedCities = viewModel.addresses.value.toList().reversed(),
        onCitySelected = {
            cityName = it
            viewModel.searchLocationAndMoveCamera(cityName, geocoder)
        },
        onClearTextSearch = { cityName = "" }
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
    suggestedCities: List<String>,
    onCitySelected: (String) -> Unit,
    onClearTextSearch: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = true,
        )
    }

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
            Column(
                modifier = Modifier
                    .padding(top = 64.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.TopCenter)
                    .height(500.dp),
            ) {
                SearchLocationTextField(
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
                    },
                    onClearTextSearch = onClearTextSearch
                )
                SuggestedCitiesList(suggestedCities, onCitySelected)
            }

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
    onSearchClick: () -> Unit,
    onClearTextSearch: () -> Unit
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
                text = stringResource(id = R.string.hint_search_location),
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
        trailingIcon = {
            value.takeIf { it.isNotEmpty() }?.let {
                IconButton(
                    onClick = onClearTextSearch
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
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
fun SuggestedCitiesList(
    suggestedCities: List<String>,
    onCitySelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomEnd = 24.dp,
                    bottomStart = 24.dp,
                    topEnd = 16.dp,
                    topStart = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
            .height(400.dp)
    ) {
        items(
            count = suggestedCities.size,
            contentType = { suggestedCities },
            key = { it }) { index ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onCitySelected(suggestedCities[index])
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = null,
                    tint = Color.Gray
                )
                Text(
                    text = suggestedCities[index],
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Divider()
        }
    }
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
