package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileScreen
import com.bitio.ui.profile.ProfileViewModel
import com.bitio.ui.shared.sharedViewModel


fun NavController.navigateToProfileScreen() {
    navigate(HomeRouteScreens.Profile.route)
}

fun NavGraphBuilder.profileRoute(navController: NavController) {
    composable(HomeRouteScreens.Profile.route) {
        val viewModel = it.sharedViewModel<ProfileViewModel>(navController = navController)
        ProfileScreen(navController = navController)
    }
}

