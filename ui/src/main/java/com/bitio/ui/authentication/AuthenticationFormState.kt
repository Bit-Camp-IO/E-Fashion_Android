package com.bitio.ui.authentication

import com.bitio.authcomponent.domain.useCases.validate.ValidForm

data class AuthenticationFormState(
    var fullName: String = "",
    val fullNameError: ValidForm = ValidForm.UnValidFullName,
    var email: String = "",
    val emailError: ValidForm = ValidForm.UnValidEmail,
    val password: String = "",
    val passwordError: ValidForm = ValidForm.UnValidPassword,
    val confirmPassword: String = "",
    val confirmPasswordError: ValidForm = ValidForm.UnValidConfirmPassword,
    val otp: String = "",
    val checkRememberMe: Boolean = false,
    val acceptedTerms: Boolean = false,
    val termsError: ValidForm = ValidForm.NotAcceptedTerm
)