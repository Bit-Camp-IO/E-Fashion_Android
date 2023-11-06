package com.bitio.ui.profile.chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileSettingsRouteScreens

internal fun NavController.navigateToChatSupportScreen() {
    navigate(ProfileSettingsRouteScreens.ChatSupport.route)
}

internal fun NavGraphBuilder.chatSupportRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.ChatSupport.route) {
        ChatSupportScreen(
            navController = navController
        )
    }
}
