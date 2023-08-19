package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.favorite.FavoriteScreen


fun NavController.navigateToFavoriteScreen() {
    navigate(RootRouteScreens.Favorite.route)
}

fun NavGraphBuilder.favoriteRoute(navController: NavController) {
    composable(RootRouteScreens.Favorite.route) {
        FavoriteScreen(navController)
    }
}

