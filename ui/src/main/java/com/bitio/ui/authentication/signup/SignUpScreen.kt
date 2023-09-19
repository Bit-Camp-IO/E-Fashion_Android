package com.bitio.ui.authentication.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bitio.authcomponent.domain.useCases.validate.ValidForm

@Composable
fun SignUpScreen(navController: NavController) {
    SignUpContent(
        fullName = "",
        email ="" ,
        password = "",
        passwordConfirmation ="" ,
        onFullNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onPasswordConfirmationChange = {},
        onClickSignUpButton = { /*TODO*/ },
        onClickLogin = { /*TODO*/ },
        onClickAcceptTermsBox = {},
        onClickClearEmail = { /*TODO*/ },
        onClickClearFullName = { /*TODO*/ },
        isClickedSignUp = false,
        isPasswordValid = ValidForm.ValidPassword,
        isConfirmPasswordValid = ValidForm.ValidPassword,
        isEmailValid =ValidForm.ValidPassword
    )
}

@Composable
fun SignUpContent(
    fullName: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickSignUpButton: () -> Unit,
    onClickLogin: () -> Unit,
    onClickAcceptTermsBox: (Boolean) -> Unit,
    onClickClearEmail: () -> Unit,
    onClickClearFullName: () -> Unit,
    isClickedSignUp: Boolean,
    isPasswordValid: ValidForm,
    isConfirmPasswordValid: ValidForm,
    isEmailValid: ValidForm
) {
    UserSignUp(
        modifier = Modifier,
        fullName = fullName,
        email = email,
        password = password,
        passwordConfirmation = passwordConfirmation,
        onFullNameChange = onFullNameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onPasswordConfirmationChange = onPasswordConfirmationChange,
        onClickSignUpButton = onClickSignUpButton,
        onClickLogIn = onClickLogin,
        onClickAcceptTermsBox = onClickAcceptTermsBox,
        onClickClearFullName = onClickClearFullName,
        onClickClearEmail = onClickClearEmail,
        isClickedSignUp = isClickedSignUp,
        isError = false
    )
}
