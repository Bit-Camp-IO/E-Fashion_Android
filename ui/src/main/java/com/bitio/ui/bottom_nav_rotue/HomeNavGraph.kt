package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = HomeRouteScreens.Home.route, route = "home") {

    }
}
