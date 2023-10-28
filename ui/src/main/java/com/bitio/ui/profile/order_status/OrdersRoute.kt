package com.bitio.ui.profile.order_status

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileRouteScreens

internal fun NavController.navigateToOrdersScreen() {
    navigate(ProfileRouteScreens.OrdersStatus.route)
}

internal fun NavGraphBuilder.ordersRoute(navController: NavController) {
    composable(ProfileRouteScreens.OrdersStatus.route) {
        OrdersScreen(navController)
    }
}


