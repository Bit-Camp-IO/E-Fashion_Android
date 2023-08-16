package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.product.favorite.FavoriteScreen
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.shared.sharedViewModel


fun NavController.navigateToFavoriteScreen() {
    navigate(HomeRouteScreens.Favorite.route)
}

fun NavGraphBuilder.favoriteRoute(navController: NavController) {
    composable(HomeRouteScreens.Favorite.route) {
        val viewModel = it.sharedViewModel<FavoriteViewModel>(navController = navController)
        FavoriteScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}

