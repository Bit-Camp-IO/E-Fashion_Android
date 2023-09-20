package com.bitio.ui.authentication

sealed class AuthFormEvent {
    data class EmailChanged(val email: String) : AuthFormEvent()
    data class PasswordChanged(val password: String) : AuthFormEvent()
    data class CheckRememberMe(val isChecked: Boolean) : AuthFormEvent()
    data class FulNameChanged(val fullName: String) : AuthFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthFormEvent()
    data class AcceptTerms(val isAccepted: Boolean) : AuthFormEvent()
    object LogIn : AuthFormEvent()
    object SignUp : AuthFormEvent()
}
