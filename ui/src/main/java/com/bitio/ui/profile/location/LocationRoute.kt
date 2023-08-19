package com.bitio.ui.profile.location

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileRouteScreens

internal fun NavController.navigateToLocationScreen() {
    navigate(ProfileRouteScreens.Location.route)
}

internal fun NavGraphBuilder.locationRoute(navController: NavController) {
    composable(ProfileRouteScreens.Location.route) {
        LocationScreen(navController)
    }
}
