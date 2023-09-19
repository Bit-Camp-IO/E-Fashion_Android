package com.bitio.ui.authentication

sealed class LoginFormEvent{
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class CheckRememberMe(val isChecked: Boolean) : LoginFormEvent()
    object LogIn : LoginFormEvent()
}
