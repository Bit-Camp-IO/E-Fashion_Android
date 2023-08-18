package com.bitio.ui.product.home.filters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.home.HomeRouteScreens

internal fun NavController.navigateToFiltersScreen() {
    navigate(HomeRouteScreens.Filters.route) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.filtersRoute(navController: NavController) {
    composable(HomeRouteScreens.Filters.route) {
        FiltersScreen(
            navController = navController
        )
    }
}