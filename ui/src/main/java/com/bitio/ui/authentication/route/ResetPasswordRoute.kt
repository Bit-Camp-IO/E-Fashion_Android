package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.reset_password.ResetPasswordScreen

internal fun NavController.navigateToResetPasswordScreen(){
    navigate(AuthRouterScreens.ResetPassword.route){
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.resetPasswordRoute(navController: NavController){
    composable(AuthRouterScreens.ResetPassword.route) {
        ResetPasswordScreen(navController)
    }
}