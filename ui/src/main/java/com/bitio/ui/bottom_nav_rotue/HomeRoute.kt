package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.home.HomeScreen

fun NavController.navigateToHomeScreen() {
    navigate(HomeRouteScreens.Home.route) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(HomeRouteScreens.Home.route) {
//        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        HomeScreen(
            navController = navController
        )
    }
}