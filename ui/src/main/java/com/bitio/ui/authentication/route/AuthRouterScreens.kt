package com.bitio.ui.authentication.route

sealed class AuthRouterScreens(val route: String) {
    object Login : AuthRouterScreens(route = "login_route")
    object ForgotPassword : AuthRouterScreens(route = "forgot_password_route")
}