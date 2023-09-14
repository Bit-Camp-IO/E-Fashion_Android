package com.bitio.efashion

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.profile.ProfileSettingsViewModel
import com.bitio.ui.theme.EFashionTheme
import com.bitio.utils.APP_TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject


class FashionActivity : ComponentActivity() {


    private val profileSettingsViewModel: ProfileSettingsViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by profileSettingsViewModel.profileSettingsUiState.collectAsState()
            val isUserLoggedIn by authenticationViewModel.isUserLoggedIn.collectAsState()
            Log.d(APP_TAG, "onCreate: $isUserLoggedIn")

            Crossfade(
                targetState = state.darkModeEnabled,
                label = "",
                animationSpec = tween(
                    durationMillis = 650,
                    easing = FastOutSlowInEasing
                )
            ) { darkModeEnabled ->
                EFashionTheme(
                    darkTheme = darkModeEnabled
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        BottomNavigationBar(checkIfLogin = isUserLoggedIn)
                    }
                }
            }
        }
    }
}

