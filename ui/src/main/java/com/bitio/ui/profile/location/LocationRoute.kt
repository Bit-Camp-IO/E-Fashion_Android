package com.bitio.ui.profile.location

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.ProfileSettingsRouteScreens

internal fun NavController.navigateToLocationScreen() {
    navigate(ProfileSettingsRouteScreens.Location.route)
}

internal fun NavGraphBuilder.locationRoute(navController: NavController) {
    composable(ProfileSettingsRouteScreens.Location.route) {
        LocationScreen(navController)
    }
}
