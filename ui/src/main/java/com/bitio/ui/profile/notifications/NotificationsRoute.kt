package com.bitio.ui.profile.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.user.ProfileRouteScreens

internal fun NavController.navigateToNotificationsScreen() {
    navigate(ProfileRouteScreens.Notifications.route)
}

internal fun NavGraphBuilder.notificationsRoute(navController: NavController) {
    composable(ProfileRouteScreens.Notifications.route) {
        NotificationsScreen(
            navController = navController
        )
    }
}
