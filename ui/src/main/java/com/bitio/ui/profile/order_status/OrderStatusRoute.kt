package com.bitio.ui.profile.order_status

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileRouteScreens

internal fun NavController.navigateToOrderStatusScreen() {
    navigate(ProfileRouteScreens.OrderStatus.route)
}

internal fun NavGraphBuilder.orderStatusRoute(navController: NavController) {
    composable(ProfileRouteScreens.OrderStatus.route) {
        OrderStatusScreen(navController)
    }
}

