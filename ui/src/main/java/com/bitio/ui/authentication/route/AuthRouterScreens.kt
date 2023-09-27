package com.bitio.ui.authentication.route

sealed class AuthRouterScreens(val route: String) {
    object Login : AuthRouterScreens(route = "login_route")
    object SignUp : AuthRouterScreens(route = "signup_route")
    object ForgotPassword : AuthRouterScreens(route = "forgot_password_route")
    object VerifyEmail : AuthRouterScreens(route = "verify_email")
    object ResetPassword : AuthRouterScreens(route = "reset_password")
}