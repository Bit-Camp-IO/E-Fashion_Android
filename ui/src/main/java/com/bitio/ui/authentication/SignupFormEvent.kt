package com.bitio.ui.authentication

sealed class SignupFormEvent {
    data class FulNameChanged(val fullName: String) : SignupFormEvent()
    data class EmailChanged(val email: String) : SignupFormEvent()
    data class PasswordChanged(val password: String) : SignupFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignupFormEvent()
    data class AcceptTerms(val isAccepted: Boolean) : SignupFormEvent()
    object SignUp : SignupFormEvent()
}
