package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.cart.CartScreen


fun NavController.navigateToCartScreen() {
    navigate(RootRouteScreens.Cart.route)
}

fun NavGraphBuilder.cartRoute(navController: NavController) {
    composable(RootRouteScreens.Cart.route) {
        CartScreen(
            navController = navController,
        )
    }
}