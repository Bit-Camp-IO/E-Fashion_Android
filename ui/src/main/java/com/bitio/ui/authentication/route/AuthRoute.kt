package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationScreen

internal fun NavController.navigateToAuthScreen() {
    navigate(AuthRouterScreens.Login.route)
}

internal fun NavGraphBuilder.authRoute(navController: NavController) {
    composable(AuthRouterScreens.Login.route) {
        AuthenticationScreen(navController)
    }
}