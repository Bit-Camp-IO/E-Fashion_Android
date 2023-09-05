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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.koin.androidx.compose.getViewModel
import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.bitio.ui.shared.shapeOfProfile
import com.bitio.utils.RealPathUtil
import java.io.File
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

    ProfileContent(
        state,
        isDarkTheme,
        onSwitchTheme,
        onClickLocationScreen = navController::navigateToLocationScreen,
        onClickOrderStatusScreen = navController::navigateToOrderStatusScreen,
        onClickChatSupportScreen = navController::navigateToChatSupportScreen,
        onClickNotificationsScreen = navController::navigateToNotificationsScreen,
        permissionViewModel::onPermissionResult,
        viewModel::addUserImage,
        onClickSaveButton = { email, fullName, phoneNumber ->
            viewModel.updateUserInfo(UserUiState(email, fullName, phoneNumber))
        },
        onFullNameChange = { viewModel.fullName.value = it },
        fullName = viewModel.fullName.value,
        onPhoneNumberChange = { viewModel.phoneNumber.value = it },
        phoneNumber = viewModel.phoneNumber.value,
        onEmailChange = { viewModel.email.value = it },
        email = viewModel.email.value,
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
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit,
    onClickSaveButton: (String, String, String) -> Unit,
    onFullNameChange: (String) -> Unit,
    fullName: String = state.profileUi.fullName,
    onPhoneNumberChange: (String) -> Unit,
    phoneNumber: String = state.profileUi.phoneNumber,
    onEmailChange: (String) -> Unit,
    email: String = state.profileUi.email
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100) }

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
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .size(screenWidth, screenHeight)
    ) {

        CustomBlurProfileImage(
            image = state.profileUi.profileImage,
            contentDescription = "state.profile.username",
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomCircleProfileImage(
                state.profileUi.profileImage,
                state.profileUi.fullName,
                isUserProfileVisible = isUserProfileVisible,
                onPermissionResult = onPermissionResult,
                onClickEditImage = onClickEditImage
            )

            VerticalSpacer8Dp()

            Text(
                text = state.profileUi.fullName,
                style = MaterialTheme.typography.titleMedium
            )

            VerticalSpacer16Dp()

            Box(
                modifier = Modifier
                    .clip(shapeOfProfile)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
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
                    onClickBack = {
                        isUserProfileVisible = false
                    },
                    modifier = Modifier.offset(x = offsetXOfUserProfile),
                    onClickSaveButton = onClickSaveButton,
                    onFullNameChange = onFullNameChange,
                    fullName = fullName.ifEmpty { state.profileUi.fullName },
                    onPhoneNumberChange = onPhoneNumberChange,
                    phoneNumber = phoneNumber.ifEmpty { state.profileUi.phoneNumber },
                    onEmailChange = onEmailChange,
                    email = email.ifEmpty { state.profileUi.email }
                )
            }

        }
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
            .blur(radius = 30.dp)
            .fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun CustomCircleProfileImage(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    isUserProfileVisible: Boolean,
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit
) {

    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = contentDescription,
            modifier = modifier
                .size(120.dp)
                .clip(RoundedCornerShape(100.dp)),
            contentScale = ContentScale.FillBounds,
        )
        if (isUserProfileVisible) {
            CustomEditIcon(
                modifier = Modifier.align(Alignment.CenterEnd),
                onPermissionResult = onPermissionResult,
                onClickEditImage = onClickEditImage
            )
        }
    }
}

@Composable
private fun CustomEditIcon(
    modifier: Modifier = Modifier,
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit,
) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                RealPathUtil.getRealPath(context, uri)?.let { path ->
                    val file = File(path)
                    onClickEditImage(file)
                }
            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val imagePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onPermissionResult(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                isGranted
            )
            if (isGranted) {
                galleryLauncher.launch("image/*")
            }
        }
    )

    IconButton(
        onClick = {
            imagePermissionResultLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        },
        modifier = modifier
            .offset(y = (-40).dp)
            .clip(RoundedCornerShape(100.dp))
            .size(24.dp)
            .background(MaterialTheme.colorScheme.secondary)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = "edit",
            tint = Porcelain
        )
    }
}