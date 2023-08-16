package com.bitio.ui.product.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.product.home.HomeScreen
import com.bitio.ui.shared.sharedViewModel

fun NavController.navigateToHomeScreen() {
    navigate(HomeRouteScreens.Home.route)
}

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(HomeRouteScreens.Home.route) {
        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        HomeScreen(
            navController = navController
        )
    }
}


