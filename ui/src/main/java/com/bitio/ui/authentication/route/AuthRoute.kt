package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationScreen
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.shared.sharedViewModel

internal fun NavController.navigateToAuthScreen() {
    navigate(AuthRouterScreens.Login.route)
}

internal fun NavGraphBuilder.authRoute(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel
) {
    composable(AuthRouterScreens.Login.route) {
//        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        AuthenticationScreen(
            viewModel = authenticationViewModel,
            navController = navController
        )
    }
}