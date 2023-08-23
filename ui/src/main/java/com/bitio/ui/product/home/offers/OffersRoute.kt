package com.bitio.ui.product.home.offers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.home.HomeRouteScreens

internal fun NavController.navigateToOffersScreen() {
    navigate(HomeRouteScreens.Offers.route) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.offersRoute(navController: NavController) {
    composable(HomeRouteScreens.Offers.route) {
        OffersScreen(
            navController = navController
        )
    }
}