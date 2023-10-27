package com.bitio.efashion

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.profile.ProfileSettingsViewModel
import com.bitio.ui.profile.notifications.viewModel.NotificationViewModel
import com.bitio.ui.profile.user.PermissionViewModel
import com.bitio.ui.shared.ImagePickerPermissionTextProvider
import com.bitio.ui.shared.LocationPermissionTextProvider
import com.bitio.ui.shared.NotificationPermissionTextProvider
import com.bitio.ui.shared.PermissionDialog
import com.bitio.ui.theme.EFashionTheme
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject


const val TOPIC = "/topics/myTopic2"


class FashionActivity : ComponentActivity() {


    private val profileSettingsViewModel: ProfileSettingsViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()
    private val permissionViewModel: PermissionViewModel by inject()
    private val notificationViewModel: NotificationViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsToRequest = arrayOf(
        ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isUserLoggedIn by authenticationViewModel.isUserLoggedIn.collectAsState()
            initialNotification(isUserLoggedIn)
            InitialMainUi(isUserLoggedIn)
        }
    }

    private fun initialNotification(isUserLoggedIn: Boolean) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (isUserLoggedIn) {
                    notificationViewModel.addDeviceTokenToNotification(task.result)
                }
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    @Composable
    private fun InitialMainUi(isUserLoggedIn: Boolean) {
        val state by profileSettingsViewModel.profileSettingsUiState.collectAsState()

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
                    CheckPermissions()
                    BottomNavigationBar(checkIfLogin = isUserLoggedIn)
                }
            }
        }
    }

    @Composable
    private fun CheckPermissions() {
        val dialogQueue = permissionViewModel.visiblePermissionDialogQueue
        val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionsToRequest.forEach { permission ->
                        permissionViewModel.onPermissionResult(
                            permission = permission,
                            isGranted = perms[permission] == true
                        )
                    }
                }
            }
        )

        LaunchedEffect(key1 = Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }
        }
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
    }

}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

