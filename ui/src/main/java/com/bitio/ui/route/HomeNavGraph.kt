package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bitio.ui.product.details.productDetailsRoute
import com.bitio.ui.product.home.HomeScreen
import com.bitio.ui.product.home.filters.filtersRoute
import com.bitio.ui.product.home.offers.offersRoute
import com.bitio.ui.product.home.zara.zaraRoute

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = "home", route = RootRouteScreens.Home.route) {
        composable("home") {
            HomeScreen(navController)
        }
        filtersRoute(navController)
        offersRoute(navController)
        zaraRoute(navController)
        productDetailsRoute(navController)
    }
}
