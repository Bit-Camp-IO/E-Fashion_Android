package com.bitio.ui.profile.user

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.profile.chat.navigateToChatSupportScreen
import com.bitio.ui.profile.user.composable.SettingApp
import com.bitio.ui.profile.user.composable.UserProfile
import com.bitio.ui.profile.location.navigateToLocationScreen
import com.bitio.ui.profile.notifications.navigateToNotificationsScreen
import com.bitio.ui.profile.order_status.navigateToOrderStatusScreen
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.Porcelain
import com.bitio.utils.profileShape
import org.koin.androidx.compose.getViewModel
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.bitio.ui.shared.ImagePermissionTextProvider
import com.bitio.ui.shared.PermissionDialog
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import com.bitio.utils.APP_TAG
import com.bitio.utils.RealPathUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProfileScreen(
    isDarkTheme: Boolean = false,
    onSwitchTheme: () -> Unit = {},
    navController: NavController,
) {
    val viewModel = getViewModel<ProfileViewModel>()
    val state by viewModel.profileUiState
    val permissionViewModel = getViewModel<PermissionViewModel>()
    val dialogQueue = permissionViewModel.visiblePermissionDialogQueue

    val context = LocalContext.current
    val activity = context as ComponentActivity


    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val path = RealPathUtil.getRealPath(context, uri)
                val file = File(path)
                viewModel.addUserImage(file)
            } catch (e: IOException) {
                Log.d(APP_TAG, "ProfileScreen: $e")
            }
        }
    }

    val imagePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionViewModel.onPermissionResult(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                isGranted = isGranted
            )
            if (isGranted) {
                galleryLauncher.launch("image/*")
            }
        }
    )


    ProfileContent(
        state,
        isDarkTheme,
        onSwitchTheme,
        onClickLocationScreen = navController::navigateToLocationScreen,
        onClickOrderStatusScreen = navController::navigateToOrderStatusScreen,
        onClickChatSupportScreen = navController::navigateToChatSupportScreen,
        onClickNotificationsScreen = navController::navigateToNotificationsScreen,
        onClickSaveButton = viewModel::updateUserInfo,
        imagePermissionResultLauncher,
        dialogQueue,
        permissionViewModel,
        activity
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ProfileContent(
    state: ProfileUiState,
    isDarkTheme: Boolean,
    onSwitchTheme: () -> Unit,
    onClickLocationScreen: () -> Unit,
    onClickOrderStatusScreen: () -> Unit,
    onClickChatSupportScreen: () -> Unit,
    onClickNotificationsScreen: () -> Unit,
    onClickSaveButton: (UserUiState) -> Unit,
    imagePermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>,
    dialogQueue: MutableState<String>,
    permissionViewModel: PermissionViewModel,
    activity: ComponentActivity
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var isUserProfileVisible by remember {
        mutableStateOf(false)
    }

    val offsetXOfProfileSettings by animateDpAsState(
        targetValue = if (isUserProfileVisible) screenWidth else 0.dp, label = ""
    )
    val offsetXOfUserProfile by animateDpAsState(
        targetValue = if (isUserProfileVisible) 0.dp else -screenWidth, label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CustomBlurProfileImage(
            image = "",
            contentDescription = "state.profile.username"
        )

        Column(
            modifier = Modifier
                .padding(top = 64.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                CustomCircleProfileImage(
                    state.profileUi.profileImage,
                    state.profileUi.fullName,
                )

                if (isUserProfileVisible) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .clip(RoundedCornerShape(100.dp))
                            .size(24.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .align(Alignment.CenterEnd)
                    ) {
                        IconButton(onClick = {
                            imagePermissionResultLauncher.launch(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "edit",
                                tint = Porcelain
                            )
                        }
                    }
                }

            }

            VerticalSpacer8Dp()

            Text(
                text = state.profileUi.fullName,
                style = MaterialTheme.typography.titleMedium
            )

            VerticalSpacer16Dp()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(profileShape)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                SettingApp(
                    isDarkTheme = isDarkTheme,
                    onSwitchTheme = onSwitchTheme,
                    modifier = Modifier.offset(x = offsetXOfProfileSettings),
                    onClickMyProfile = { isUserProfileVisible = true },
                    onClickLocationScreen = onClickLocationScreen,
                    onClickOrderStatusScreen = onClickOrderStatusScreen,
                    onClickChatSupportScreen = onClickChatSupportScreen,
                    onClickNotificationsScreen = onClickNotificationsScreen,
                )



                UserProfile(
                    state.profileUi,
                    onClickBack = {
                        isUserProfileVisible = false
                    },
                    modifier = Modifier.offset(x = offsetXOfUserProfile),
                    onClickSaveButton = { fullName, phoneNumber, email ->
                        onClickSaveButton(
                            UserUiState(
                                email, fullName, phoneNumber
                            )
                        )
                    },
                )
            }

            if (dialogQueue.value.isNotEmpty()) {
                dialogQueue.value.let { permission ->
                    PermissionDialog(
                        permissionTextProvider = ImagePermissionTextProvider(),
                        isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            activity,
                            permission
                        ),
                        onDismiss = permissionViewModel::dismissDialog,
                        onOkClick = {
                            permissionViewModel.dismissDialog()
                            imagePermissionResultLauncher.launch(permission)
                        },
                        onGoToAppSettingsClick = { openAppSettings(activity) },
                    )
                }
            }
        }
    }
}

private fun openAppSettings(activity: ComponentActivity) {
    with(activity) {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }
}

@Composable
private fun CustomBlurProfileImage(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxSize()
            .blur(radius = 30.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun CustomCircleProfileImage(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {

    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = contentDescription,
        modifier = modifier
            .size(120.dp)
            .clip(RoundedCornerShape(100.dp)),
        contentScale = ContentScale.FillBounds,
    )
}

fun convertJpgToPng(file: File): File? {
    try {
        if (!file.exists()) {
            return null
        }

        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)

        val pngFilePath = file.absolutePath.replace(".jpg", ".png")
        val pngFile = File(pngFilePath)
        val outputStream = FileOutputStream(pngFile)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        file.delete()
        return pngFile
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}