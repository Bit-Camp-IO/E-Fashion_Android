package com.bitio.ui.profile.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileSettingsRouteScreens

internal fun NavController.navigateToNotificationsScreen() {
    navigate(ProfileSettingsRouteScreens.Notifications.route)
}

internal fun NavGraphBuilder.notificationsRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.Notifications.route) {
        NotificationsScreen(
            navController = navController
        )
    }
}
