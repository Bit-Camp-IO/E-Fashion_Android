package com.bitio.ui.profile.user

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileSettingsRouteScreens

internal fun NavController.navigateToEditProfileScreen() {
    navigate(ProfileSettingsRouteScreens.UserProfile.route)
}

internal fun NavGraphBuilder.editProfileRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.UserProfile.route) {
        EditProfileScreen(navController)
    }
}





internal fun NavController.navigateToChangePasswordScreen() {
    navigate(ProfileSettingsRouteScreens.ChangePassword.route)
}

internal fun NavGraphBuilder.changePasswordRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.ChangePassword.route) {
        ChangePasswordScreen(navController)
    }
}
