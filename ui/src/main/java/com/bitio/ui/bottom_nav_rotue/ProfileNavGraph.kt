package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bitio.ui.order_status.OrderStatusViewModel
import com.bitio.ui.profile.ProfileScreen
import com.bitio.ui.profile.ProfileViewModel
import com.bitio.ui.profile.route.chatSupportRoute
import com.bitio.ui.profile.route.locationRoute
import com.bitio.ui.profile.route.notificationsRoute
import com.bitio.ui.profile.route.orderStatusRoute


fun NavGraphBuilder.profileGraph(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    orderStatusViewModel: OrderStatusViewModel
) {
    navigation(startDestination = "profile", route = HomeRouteScreens.Profile.route) {
        composable("profile") {
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        locationRoute(navController = navController)
        orderStatusRoute(
            navController = navController,
            orderStatusViewModel = orderStatusViewModel
        )
        chatSupportRoute(navController = navController)
        notificationsRoute(navController = navController)
    }
}