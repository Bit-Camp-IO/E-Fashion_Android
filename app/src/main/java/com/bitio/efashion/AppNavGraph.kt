package com.bitio.efashion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitio.ui.authentication.route.authGraph
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens
import com.bitio.ui.bottom_nav_rotue.cartRoute
import com.bitio.ui.bottom_nav_rotue.favoriteRoute
import com.bitio.ui.bottom_nav_rotue.homeGraph
import com.bitio.ui.bottom_nav_rotue.homeRoute
import com.bitio.ui.bottom_nav_rotue.profileRoute

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HomeRouteScreens.Home.route) {
        homeRoute(navController)
        cartRoute(navController)
        favoriteRoute(navController)
        profileRoute(navController)

        authGraph(navController = navController)
    }
}