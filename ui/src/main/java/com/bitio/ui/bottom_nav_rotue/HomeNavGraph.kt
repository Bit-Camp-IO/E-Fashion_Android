package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bitio.ui.product.home.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = "home", route = HomeRouteScreens.Home.route) {
        composable("home") {
            HomeScreen(
                navController = navController
            )
        }
    }
}
