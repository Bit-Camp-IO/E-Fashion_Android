package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationScreen
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.shared.sharedViewModel

fun NavController.navigateToAuthScreen() {
    navigate(AuthRouterScreens.Login.route)
}

fun NavGraphBuilder.authRoute(navController: NavController) {
    composable(AuthRouterScreens.ForgotPassword.route) {
        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        AuthenticationScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}