package com.bitio.ui.authentication.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.ForgotPasswordScreen
import com.bitio.ui.shared.sharedViewModel

internal fun NavController.navigateToForgotPasswordScreen(){
    navigate(AuthRouterScreens.ForgotPassword.route){
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.forgotPasswordRoute(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel
){
    composable(AuthRouterScreens.ForgotPassword.route) {
//        val viewModel = it.sharedViewModel<AuthenticationViewModel>(navController = navController)
        ForgotPasswordScreen(navController = navController)
    }
}