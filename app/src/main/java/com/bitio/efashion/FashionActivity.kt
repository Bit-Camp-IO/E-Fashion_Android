package com.bitio.efashion

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.bitio.ui.profile.user.PermissionViewModel
import com.bitio.ui.shared.ImagePickerPermissionTextProvider
import com.bitio.ui.shared.LocationPermissionTextProvider
import com.bitio.ui.shared.PermissionDialog
import com.bitio.ui.theme.EFashionTheme
import com.bitio.utils.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingApi
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject


class FashionActivity : ComponentActivity() {


    private val profileSettingsViewModel: ProfileSettingsViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()
    private val permissionViewModel: PermissionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dialogQueue = permissionViewModel.visiblePermissionDialogQueue
            val state by profileSettingsViewModel.profileSettingsUiState.collectAsState()
            val isUserLoggedIn by authenticationViewModel.isUserLoggedIn.collectAsState()

            askPermissions()

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

                        dialogQueue
                            .reversed()
                            .forEach { permission ->
                                PermissionDialog(
                                    permissionTextProvider = when (permission) {
                                        Manifest.permission.READ_MEDIA_IMAGES -> {
                                            ImagePickerPermissionTextProvider()
                                        }

                                        ACCESS_FINE_LOCATION -> {
                                            LocationPermissionTextProvider()
                                        }

                                        else -> return@forEach
                                    },
                                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                        permission
                                    ),
                                    onDismiss = permissionViewModel::dismissDialog,
                                    onOkClick = {
                                        permissionViewModel.dismissDialog()
                                    },
                                    onGoToAppSettingsClick = ::openAppSettings
                                )
                            }

                        BottomNavigationBar(checkIfLogin = isUserLoggedIn)
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionViewModel.onPermissionResult(
            permission = ACCESS_FINE_LOCATION,
            isGranted = isGranted
        )
        if (isGranted) {

        }
    }

    private fun askPermissions() {
        if (!hasLocationPermission()) {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

