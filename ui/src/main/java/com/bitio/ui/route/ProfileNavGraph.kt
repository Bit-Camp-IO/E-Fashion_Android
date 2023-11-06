package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bitio.ui.profile.UserProfileScreen
import com.bitio.ui.profile.chat.chatSupportRoute
import com.bitio.ui.profile.location.locationRoute
import com.bitio.ui.profile.notifications.notificationsRoute
import com.bitio.ui.profile.order_status.ordersRoute
import com.bitio.ui.profile.user.changeEmailRoute
import com.bitio.ui.profile.user.changePasswordRoute
import com.bitio.ui.profile.user.editProfileRoute


fun NavGraphBuilder.profileGraph(
    navController: NavController,
) {
    navigation(startDestination = "profile", route = RootRouteScreens.Profile.route) {
        composable("profile") {
            UserProfileScreen(navController = navController)
        }
        locationRoute(navController)
        ordersRoute(navController)
        chatSupportRoute(navController)
        notificationsRoute(navController)
        editProfileRoute(navController)
        changeEmailRoute(navController)
        changePasswordRoute(navController)
    }
}