package com.bitio.ui.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.favorite.FavoriteScreen
import com.bitio.ui.product.favorite.FavoriteViewModel


fun NavController.navigateToFavoriteScreen() {
    navigate(RootRouteScreens.Favorite.route)
}

fun NavGraphBuilder.favoriteRoute(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
) {
    composable(RootRouteScreens.Favorite.route) {
        FavoriteScreen(
            navController = navController,
            viewModel = favoriteViewModel,
        )
    }
}

