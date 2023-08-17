package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.favorite.FavoriteScreen
import com.bitio.ui.product.favorite.FavoriteViewModel


fun NavController.navigateToFavoriteScreen() {
    navigate(HomeRouteScreens.Favorite.route)
}

fun NavGraphBuilder.favoriteRoute(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    composable(HomeRouteScreens.Favorite.route) {
        FavoriteScreen(navController = navController, viewModel = favoriteViewModel)
    }
}

