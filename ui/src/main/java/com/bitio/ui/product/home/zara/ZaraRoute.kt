package com.bitio.ui.product.home.zara

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.home.HomeRouteScreens

internal fun NavController.navigateToZaraScreen() {
    navigate(HomeRouteScreens.Zara.route) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.zaraRoute(
    navController: NavController
) {
    composable(HomeRouteScreens.Zara.route) {
        AllProductsScreen(navController = navController)
    }
}