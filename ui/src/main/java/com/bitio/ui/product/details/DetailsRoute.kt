package com.bitio.ui.product.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bitio.ui.product.home.HomeRouteScreens


internal fun NavController.navigateToProductDetailsScreen(id: Int) {
    navigate("${HomeRouteScreens.ProductDetails.route}/$id") {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.productDetailsRoute(
    navController: NavController
) {
    composable(
        "${HomeRouteScreens.ProductDetails.route}/{${DetailsArgsRoute.PRODUCT_ID}}",
        arguments = listOf(
            navArgument(DetailsArgsRoute.PRODUCT_ID) {
                NavType.IntType
            }
        )
    ) {
        DetailsScreen(navController = navController)
    }
}

class DetailsArgsRoute(savedStateHandle: SavedStateHandle) {
    val id: Int = checkNotNull(savedStateHandle[PRODUCT_ID]).toString().toInt()

    companion object {
        const val PRODUCT_ID = "id"
    }
}