package com.bitio.ui.profile

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
import com.bitio.ui.profile.settings.SettingApp
import com.bitio.ui.profile.location.navigateToLocationScreen
import com.bitio.ui.profile.notifications.navigateToNotificationsScreen
import com.bitio.ui.profile.order_status.navigateToOrderStatusScreen
import com.bitio.ui.theme.Porcelain
import org.koin.androidx.compose.getViewModel
import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.bitio.ui.profile.user.PermissionViewModel
import com.bitio.ui.profile.user.ProfileUi
import com.bitio.ui.profile.user.UserProfile
import com.bitio.ui.profile.user.UserUiState
import com.bitio.ui.profile.user.UserViewModel
import com.bitio.ui.shared.shapeOfImageProfile
import com.bitio.ui.shared.shapeOfProfile
import com.bitio.utils.APP_TAG
import com.bitio.utils.RealPathUtil
import java.io.File
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProfileScreen(
    navController: NavController,
) {
    val userViewModel = getViewModel<UserViewModel>()
    val state by userViewModel.profileUiState
    val profileSettingsViewModel = getViewModel<ProfileSettingsViewModel>()
    val profileSettingsState by profileSettingsViewModel.profileSettingsUiState.collectAsState()
    val permissionViewModel = getViewModel<PermissionViewModel>()

    ProfileContent(
        profileUiState = state.profileUi,
        darkModeEnabled = profileSettingsState.darkModeEnabled,
        onSwitchTheme = profileSettingsViewModel::onSwitchTheme,
        onClickLocationScreen = navController::navigateToLocationScreen,
        onClickOrderStatusScreen = navController::navigateToOrderStatusScreen,
        onClickChatSupportScreen = navController::navigateToChatSupportScreen,
        onClickNotificationsScreen = navController::navigateToNotificationsScreen,
        permissionViewModel::onPermissionResult,
        userViewModel::addUserImage,
        onClickSaveButton = { fullName, phoneNumber, email ->
            userViewModel.updateUserInfo(
                UserUiState(
                    email = email,
                    fullName = fullName,
                    phoneNumber = phoneNumber
                )
            )
        },
        onFullNameChange = { userViewModel.fullName.value = it },
        fullName = userViewModel.fullName.value,
        onPhoneNumberChange = { userViewModel.phoneNumber.value = it },
        phoneNumber = userViewModel.phoneNumber.value,
        onEmailChange = { userViewModel.email.value = it },
        email = userViewModel.email.value,
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ProfileContent(
    profileUiState: ProfileUi,
    darkModeEnabled: Boolean,
    onSwitchTheme: (Boolean) -> Unit,
    onClickLocationScreen: () -> Unit,
    onClickOrderStatusScreen: () -> Unit,
    onClickChatSupportScreen: () -> Unit,
    onClickNotificationsScreen: () -> Unit,
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit,
    onClickSaveButton: (String, String, String) -> Unit,
    onFullNameChange: (String) -> Unit,
    fullName: String,
    onPhoneNumberChange: (String) -> Unit,
    phoneNumber: String,
    onEmailChange: (String) -> Unit,
    email: String,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var isUserProfileVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .size(screenWidth, screenHeight)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Header(
            painter = rememberAsyncImagePainter(model = profileUiState.profileImage),
            modifier = Modifier
                .height(screenHeight / 1.5f)
                .graphicsLayer {
                    alpha = 1 - (3f * scrollState.value.toFloat() / scrollState.maxValue)
                }
        )

        Body(
            painter = rememberAsyncImagePainter(model = profileUiState.profileImage),
            isUserProfileVisible = isUserProfileVisible,
            onPermissionResult = onPermissionResult,
            onClickEditImage = onClickEditImage,
            fullName = profileUiState.fullName,
            modifier = Modifier.graphicsLayer {
                scaleX = 1 - (0.5 * (scrollState.value.toFloat() / scrollState.maxValue)).toFloat()
                scaleY = 1 - (0.5 * (scrollState.value.toFloat() / scrollState.maxValue)).toFloat()
                translationX = scrollState.value.toFloat()
                translationY = (scrollState.value.toFloat() / screenHeight.value)
            },
            isFullNameAndEditIconVisible = scrollState.value.toFloat() == 0f
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = screenHeight / 3.5f)
        ) {
            Footer(
                darkModeEnabled = darkModeEnabled,
                onSwitchTheme = onSwitchTheme,
                onClickLocationScreen = onClickLocationScreen,
                onClickOrderStatusScreen = onClickOrderStatusScreen,
                onClickChatSupportScreen = onClickChatSupportScreen,
                onClickNotificationsScreen = onClickNotificationsScreen,
                onClickSaveButton = onClickSaveButton,
                onFullNameChange = onFullNameChange,
                fullName = fullName,
                onPhoneNumberChange = onPhoneNumberChange,
                phoneNumber = phoneNumber,
                onEmailChange = onEmailChange,
                email = email,
                isUserProfileVisible = isUserProfileVisible,
                onSwitchBetweenProfile = {
                    isUserProfileVisible = it
                },
                screenWidth = screenWidth
            )
        }
    }
}

@Composable
private fun Header(
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .clip(shapeOfImageProfile)
            .fillMaxWidth()
            .blur(radius = 20.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun Body(
    painter: Painter,
    isUserProfileVisible: Boolean,
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit,
    fullName: String,
    modifier: Modifier = Modifier,
    isFullNameAndEditIconVisible: Boolean
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 40.dp)
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.FillWidth,
            )
            if (isUserProfileVisible && isFullNameAndEditIconVisible) {
                CustomEditIcon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onPermissionResult = onPermissionResult,
                    onClickEditImage = onClickEditImage
                )
            }
        }
        if (isFullNameAndEditIconVisible) {
            Text(
                text = fullName,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun Footer(
    darkModeEnabled: Boolean,
    onSwitchTheme: (Boolean) -> Unit,
    onClickLocationScreen: () -> Unit,
    onClickOrderStatusScreen: () -> Unit,
    onClickChatSupportScreen: () -> Unit,
    onClickNotificationsScreen: () -> Unit,
    onClickSaveButton: (String, String, String) -> Unit,
    onFullNameChange: (String) -> Unit,
    fullName: String,
    onPhoneNumberChange: (String) -> Unit,
    phoneNumber: String,
    onEmailChange: (String) -> Unit,
    email: String,
    isUserProfileVisible: Boolean,
    onSwitchBetweenProfile: (Boolean) -> Unit,
    screenWidth: Dp,
) {

    val offsetXOfProfileSettings by animateDpAsState(
        targetValue = if (isUserProfileVisible) screenWidth else 0.dp, label = ""
    )
    val offsetXOfUserProfile by animateDpAsState(
        targetValue = if (isUserProfileVisible) 0.dp else -screenWidth, label = ""
    )


    Column(
        modifier = Modifier
            .wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            modifier = Modifier
                .clip(shapeOfProfile)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            SettingApp(
                darkModeEnabled = darkModeEnabled,
                onSwitchTheme = onSwitchTheme,
                modifier = Modifier.offset(x = offsetXOfProfileSettings),
                onClickMyProfile = { onSwitchBetweenProfile(true) },
                onClickLocationScreen = onClickLocationScreen,
                onClickOrderStatusScreen = onClickOrderStatusScreen,
                onClickChatSupportScreen = onClickChatSupportScreen,
                onClickNotificationsScreen = onClickNotificationsScreen,
            )

            UserProfile(
                onClickBack = {
                    onSwitchBetweenProfile(false)
                },
                modifier = Modifier.offset(x = offsetXOfUserProfile),
                onClickSaveButton = onClickSaveButton,
                onFullNameChange = onFullNameChange,
                fullName = fullName,
                onPhoneNumberChange = onPhoneNumberChange,
                phoneNumber = phoneNumber,
                onEmailChange = onEmailChange,
                email = email
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