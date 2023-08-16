package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthRouterScreens.Login.route, route = "auth") {
        authRoute(navController)
        forgotPasswordRoute(navController)
    }
}
