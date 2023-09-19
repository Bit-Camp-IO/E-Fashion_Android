package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.signup.SignUpScreen

internal fun NavController.navigateToSignUpScreen() {
    navigate(AuthRouterScreens.SignUp.route)
}

internal fun NavGraphBuilder.signUpRoute(navController: NavController) {
    composable(AuthRouterScreens.SignUp.route) {
        SignUpScreen(navController)
    }
}