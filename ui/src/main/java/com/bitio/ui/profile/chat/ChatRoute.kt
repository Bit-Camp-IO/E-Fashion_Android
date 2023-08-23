package com.bitio.ui.profile.chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileRouteScreens

internal fun NavController.navigateToChatSupportScreen() {
    navigate(ProfileRouteScreens.ChatSupport.route)
}

internal fun NavGraphBuilder.chatSupportRoute(navController: NavController) {
    composable(ProfileRouteScreens.ChatSupport.route) {
        ChatScreen(
            navController = navController
        )
    }
}
