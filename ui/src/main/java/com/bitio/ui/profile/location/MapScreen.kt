package com.bitio.ui.profile.location

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(navController: NavController) {

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = true)
    }

    val cameraPositionState = rememberCameraPositionState()

    var lat by remember {
        mutableStateOf(0.0)
    }

    var lng by remember {
        mutableStateOf(0.0)
    }

    Scaffold {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(),
            uiSettings = uiSettings,
            onMapLongClick = {
                lat = it.latitude
                lng = it.longitude
            },
            cameraPositionState = cameraPositionState

        ) {
            Marker(
                position = LatLng(lat, lng),
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
    }

}