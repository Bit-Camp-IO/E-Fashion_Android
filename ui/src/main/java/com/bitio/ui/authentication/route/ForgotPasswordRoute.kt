package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.forgot_password.ForgotPasswordScreen

internal fun NavController.navigateToForgotPasswordScreen(){
    navigate(AuthRouterScreens.ForgotPassword.route){
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.forgotPasswordRoute(navController: NavController){
    composable(AuthRouterScreens.ForgotPassword.route) {
        ForgotPasswordScreen(navController)
    }
}