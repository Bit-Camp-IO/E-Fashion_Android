package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.login.LoginScreen

internal fun NavController.navigateToLoginScreen() {
    navigate(AuthRouterScreens.Login.route)
}

internal fun NavGraphBuilder.loginRoute(navController: NavController) {
    composable(AuthRouterScreens.Login.route) {
        LoginScreen(navController)
    }
}