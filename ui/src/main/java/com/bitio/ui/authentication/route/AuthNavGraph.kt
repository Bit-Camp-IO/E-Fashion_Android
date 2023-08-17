package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.bitio.ui.authentication.AuthenticationViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel
) {
    navigation(startDestination = AuthRouterScreens.Login.route, route = "auth") {
        authRoute(
            navController,
            authenticationViewModel
        )
        forgotPasswordRoute(navController, authenticationViewModel)
    }
}
