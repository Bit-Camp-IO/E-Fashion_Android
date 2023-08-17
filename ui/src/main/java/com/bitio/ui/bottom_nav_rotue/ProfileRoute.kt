package com.bitio.ui.bottom_nav_rotue

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileScreen
import com.bitio.ui.profile.ProfileViewModel


fun NavController.navigateToProfileScreen() {
    navigate(HomeRouteScreens.Profile.route)
}

fun NavGraphBuilder.profileRoute(navController: NavController, profileViewModel: ProfileViewModel) {
    composable(HomeRouteScreens.Profile.route) {
        ProfileScreen(
            navController = navController,
            viewModel = profileViewModel
        )
    }
}

