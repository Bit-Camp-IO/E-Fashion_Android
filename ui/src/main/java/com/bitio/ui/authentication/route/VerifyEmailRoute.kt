package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.verify_email.VerifyEmailScreen

internal fun NavController.navigateToVerifyEmailScreen(){
    navigate(AuthRouterScreens.VerifyEmail.route){
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.verifyEmailRoute(navController: NavController){
    composable(AuthRouterScreens.VerifyEmail.route) {
        VerifyEmailScreen(navController)
    }
}