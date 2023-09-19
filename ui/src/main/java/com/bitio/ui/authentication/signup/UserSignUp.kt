package com.bitio.ui.authentication.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.authentication.composable.AuthPasswordTextField
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.ProfileCustomTextField
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp

@Composable
fun UserSignUp(
    modifier: Modifier = Modifier,
    fullName: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickSignUpButton: () -> Unit,
    onClickLogIn: () -> Unit,
    onClickAcceptTermsBox: (Boolean) -> Unit,
    onClickClearFullName: () -> Unit,
    onClickClearEmail: () -> Unit,
    isClickedSignUp: Boolean,
    isError: Boolean,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        VerticalSpacer32Dp()
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(horizontal = 24.dp, vertical = 40.dp)
        )

        VerticalSpacer40Dp()

        ProfileCustomTextField(
            value = fullName,
            placeholder = stringResource(id = R.string.full_name),
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = onFullNameChange,
            onClickClearText = onClickClearFullName,
            emailError = ""
        )
        VerticalSpacer16Dp()
        ProfileCustomTextField(
            value = email,
            placeholder = stringResource(id = R.string.email),
            leadingIcon = painterResource(id = R.drawable.email),
            onValueChange = onEmailChange,
            onClickClearText = onClickClearEmail,
            emailError = ""
        )
        VerticalSpacer16Dp()
        AuthPasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            imeAction = ImeAction.Done,
            isPasswordValid = isError,
            passwordError = ""
        )
        VerticalSpacer16Dp()
        AuthPasswordTextField(
            value = passwordConfirmation,
            onValueChange = onPasswordConfirmationChange,
            imeAction = ImeAction.Done,
            placeholder = stringResource(id = R.string.password_confirm),
            isPasswordValid = isError,
            passwordError = ""
        )
        VerticalSpacer16Dp()
        CheckBoxContainer(onClickCheckedBox = onClickAcceptTermsBox)
        VerticalSpacer32Dp()
        CustomButtonForm(
            title = stringResource(id = R.string.sign_up),
            onClickButton = onClickSignUpButton,
            isLoading = isClickedSignUp
        )
        VerticalSpacer32Dp()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.already_have_an_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            TextButton(onClick = onClickLogIn) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun CheckBoxContainer(
    onClickCheckedBox: (Boolean) -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {

        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onClickCheckedBox(it)
                isChecked = it
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.tertiary,
                checkmarkColor = MaterialTheme.colorScheme.background
            )
        )
        Text(
            text = stringResource(id = R.string.i_agree),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
    }
}