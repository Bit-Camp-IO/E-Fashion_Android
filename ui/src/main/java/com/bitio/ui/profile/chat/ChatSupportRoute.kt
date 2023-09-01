package com.bitio.ui.profile.chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.user.ProfileRouteScreens

internal fun NavController.navigateToChatSupportScreen() {
    navigate(ProfileRouteScreens.ChatSupport.route)
}

internal fun NavGraphBuilder.chatSupportRoute(navController: NavController) {
    composable(ProfileRouteScreens.ChatSupport.route) {
        ChatSupportScreen(
            navController = navController
        )
    }
}
