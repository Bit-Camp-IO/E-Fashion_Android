package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.cart.CartScreen

fun NavController.navigateToCartScreen() {
    navigate(HomeRouteScreens.Cart.route)
}

fun NavGraphBuilder.cartRoute(navController: NavController) {
    composable(HomeRouteScreens.Cart.route) {
        /* just replace the AuthenticationViewModel with your ViewModel
                val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        */
        CartScreen(
            navController = navController
        )
    }
}