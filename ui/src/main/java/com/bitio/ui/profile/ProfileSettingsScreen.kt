package com.bitio.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.R
import com.bitio.ui.profile.chat.navigateToChatSupportScreen
import com.bitio.ui.theme.Porcelain
import org.koin.androidx.compose.getViewModel
import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.profile.location.navigateToLocationScreen
import com.bitio.ui.profile.order_status.navigateToOrdersScreen
import com.bitio.ui.profile.user.PermissionViewModel
import com.bitio.ui.profile.user.UserViewModel
import com.bitio.ui.profile.user.navigateToChangePasswordScreen
import com.bitio.ui.profile.user.navigateToEditProfileScreen
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.utils.RealPathUtil
import com.bitio.utils.hasLocationPermission
import java.io.File
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun UserProfileScreen(
    navController: NavController,
) {
    val userViewModel = getViewModel<UserViewModel>()
    val state by userViewModel.userProfileUiState
    val profileSettingsViewModel = getViewModel<ProfileSettingsViewModel>()
    val profileSettingsState by profileSettingsViewModel.profileSettingsUiState.collectAsState()
    val permissionViewModel = getViewModel<PermissionViewModel>()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isGranted
            )
        }
    )
    val context = LocalContext.current
    ProfileContent(
        profileUiState = state.profileUi,
        isDarkModeEnabled = profileSettingsState.darkModeEnabled,
        isNotificationEnabled = false,
        onSwitchTheme = profileSettingsViewModel::onSwitchTheme,
        onSwitchNotification = {},
        onLocationScreenClicked = {
            if (!context.hasLocationPermission()) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                navController.navigateToLocationScreen()
            }
        },
        onOrderStatusScreenClicked = navController::navigateToOrdersScreen,
        onChatSupportScreenClicked = navController::navigateToChatSupportScreen,
        onMyProfileClicked = navController::navigateToEditProfileScreen,
        onChangePasswordScreenClicked = navController::navigateToChangePasswordScreen,
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ProfileContent(
    profileUiState: UserProfile,
    isDarkModeEnabled: Boolean,
    isNotificationEnabled: Boolean,
    onSwitchTheme: (Boolean) -> Unit,
    onSwitchNotification: (Boolean) -> Unit,
    onMyProfileClicked: () -> Unit,
    onLocationScreenClicked: () -> Unit,
    onOrderStatusScreenClicked: () -> Unit,
    onChatSupportScreenClicked: () -> Unit,
    onChangePasswordScreenClicked: () -> Unit,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Header(
            painter = rememberAsyncImagePainter(model = profileUiState.profileImage),
            fullName = profileUiState.fullName
        )
        Body(onLogoutClick = {})

        Footer(
            isDarkModeEnabled = isDarkModeEnabled,
            isNotificationEnabled = isNotificationEnabled,
            onSwitchTheme = onSwitchTheme,
            onSwitchNotification = onSwitchNotification,
            onMyProfileClicked = onMyProfileClicked,
            onLocationScreenClicked = onLocationScreenClicked,
            onOrderStatusScreenClicked = onOrderStatusScreenClicked,
            onChatSupportScreenClicked = onChatSupportScreenClicked,
            onChangePasswordScreenClicked = onChangePasswordScreenClicked,
        )
    }
}

@Composable
private fun Header(
    painter: Painter,
    fullName: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(4.dp)
                .clip(RoundedCornerShape(100.dp)),
            contentScale = ContentScale.FillWidth,
        )
        VerticalSpacer8Dp()
        Text(
            text = fullName,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun Body(
    onLogoutClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        IconButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                tint = MaterialTheme.colorScheme.onPrimary            )
        }
        Text(
            text = stringResource(id = R.string.profile_settings),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Footer(
    isDarkModeEnabled: Boolean,
    isNotificationEnabled: Boolean,
    onSwitchTheme: (Boolean) -> Unit,
    onSwitchNotification: (Boolean) -> Unit,
    onMyProfileClicked: () -> Unit,
    onLocationScreenClicked: () -> Unit,
    onOrderStatusScreenClicked: () -> Unit,
    onChatSupportScreenClicked: () -> Unit,
    onChangePasswordScreenClicked: () -> Unit,
) {
    SettingApp(
        isDarkModeEnabled = isDarkModeEnabled,
        isNotificationEnabled = isNotificationEnabled,
        onSwitchTheme = onSwitchTheme,
        onSwitchNotification = onSwitchNotification,
        onMyProfileClicked = onMyProfileClicked,
        onLocationScreenClicked = onLocationScreenClicked,
        onOrderStatusScreenClicked = onOrderStatusScreenClicked,
        onChatSupportScreenClicked = onChatSupportScreenClicked,
        onChangePasswordScreenClicked = onChangePasswordScreenClicked,
    )
}