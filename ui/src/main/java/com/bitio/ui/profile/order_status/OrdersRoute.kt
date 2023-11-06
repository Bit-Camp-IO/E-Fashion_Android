package com.bitio.ui.profile.order_status

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileSettingsRouteScreens

internal fun NavController.navigateToOrdersScreen() {
    navigate(ProfileSettingsRouteScreens.OrdersStatus.route)
}

internal fun NavGraphBuilder.ordersRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.OrdersStatus.route) {
        OrdersScreen(navController)
    }
}


