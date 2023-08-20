package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bitio.ui.profile.ProfileScreen
import com.bitio.ui.profile.chat.chatSupportRoute
import com.bitio.ui.profile.location.locationRoute
import com.bitio.ui.profile.notifications.notificationsRoute
import com.bitio.ui.profile.order_status.orderStatusRoute


fun NavGraphBuilder.profileGraph(
    navController: NavController,
) {
    navigation(startDestination = "profile", route = RootRouteScreens.Profile.route) {
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        locationRoute(navController)
        orderStatusRoute(navController)
        chatSupportRoute(navController)
        notificationsRoute(navController)
    }
}