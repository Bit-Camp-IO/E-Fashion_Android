package com.bitio.ui.product.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = HomeRouteScreens.Home.route, route = "home") {
        authRoute(navController)
        forgotPasswordRoute(navController)
    }
}
