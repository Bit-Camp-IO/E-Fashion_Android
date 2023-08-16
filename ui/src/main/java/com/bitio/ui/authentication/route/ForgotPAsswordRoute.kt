package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.ForgotPasswordScreen
import com.bitio.ui.shared.sharedViewModel

fun NavController.navigateToForgotPasswordScreen(){
    navigate(AuthRouterScreens.ForgotPassword.route)
}

fun NavGraphBuilder.forgotPasswordRoute(navController: NavController){
    composable(AuthRouterScreens.ForgotPassword.route) {
        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        ForgotPasswordScreen(navController = navController)
    }
}