package com.bitio.efashion

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.profile.ProfileSettingsViewModel
import com.bitio.ui.profile.user.PermissionViewModel
import com.bitio.ui.shared.ImagePickerPermissionTextProvider
import com.bitio.ui.shared.LocationPermissionTextProvider
import com.bitio.ui.shared.NotificationPermissionTextProvider
import com.bitio.ui.shared.PermissionDialog
import com.bitio.ui.theme.EFashionTheme
import com.bitio.utils.TAG_APP
import com.bitio.utils.hasLocationPermission
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


const val TOPIC = "/topics/myTopic2"


class FashionActivity : ComponentActivity() {


    private val profileSettingsViewModel: ProfileSettingsViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()
    private val permissionViewModel: PermissionViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsToRequest = arrayOf(
        ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d(TAG_APP, "Fetching FCM registration token failed: ${task.result}")
//            } else {
//                Log.w(TAG_APP, "Fetching FCM registration token failed", task.exception)
//            }
//        }

        askNotificationPermissions()
        askLocationPermissions()

        setContent {
            val dialogQueue = permissionViewModel.visiblePermissionDialogQueue
            val state by profileSettingsViewModel.profileSettingsUiState.collectAsState()
            val isUserLoggedIn by authenticationViewModel.isUserLoggedIn.collectAsState()

            val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { perms ->
                    permissionsToRequest.forEach { permission ->
                        permissionViewModel.onPermissionResult(
                            permission = permission,
                            isGranted = perms[permission] == true
                        )
                    }
                }
            )

            LaunchedEffect(key1 = Unit) {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }

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

                                        Manifest.permission.POST_NOTIFICATIONS -> {
                                            NotificationPermissionTextProvider()
                                        }

                                        else -> return@forEach
                                    },
                                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                        permission
                                    ),
                                    onDismiss = permissionViewModel::dismissDialog,
                                    onOkClick = {
                                        permissionViewModel.dismissDialog()
                                        multiplePermissionResultLauncher.launch(
                                            arrayOf(permission)
                                        )
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
    }

    private fun askLocationPermissions() {
        if (!hasLocationPermission()) {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionViewModel.onPermissionResult(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                isGranted = isGranted
            )
        }
    }

    private fun askNotificationPermissions() {
        if (!askNotificationPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun askNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false
    }

}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

