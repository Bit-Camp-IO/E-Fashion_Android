package com.bitio.efashion

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.bitio.ui.authentication.AuthenticationViewModel

import com.bitio.ui.theme.EFashionTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject


class FashionActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) -> {
            // TODO
        }

        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()

        setContent {
            val isDarkTheme by remember {
                mutableStateOf(false)
            }
            val navController = rememberNavController()

            EFashionTheme(darkTheme = isDarkTheme) {
                BottomNavigationBar(navController, true)
            }
        }
    }
}
