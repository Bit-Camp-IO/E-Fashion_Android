package com.bitio.ui.authentication.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.authentication.composable.AuthButtonForm
import com.bitio.ui.authentication.composable.AuthTextField
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp

@Composable
internal fun ForgotPasswordBody(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    onClickClearEmail: () -> Unit,
    onClickRecoverButton: () -> Unit,
    isEnabled: Boolean,
    isEmailValid: Boolean,
    emailError: String,
    isSubmit: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(horizontal = 24.dp, vertical = 40.dp)
        )
        Text(
            text = stringResource(id = R.string.details_of_reset_password),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary
        )

        VerticalSpacer40Dp()

        AuthTextField(
            value = email,
            placeholder = stringResource(id = R.string.your_email),
            leadingIcon = painterResource(id = R.drawable.email),
            keyboardType = KeyboardType.Email,
            onValueChange = onEmailChange,
            onClickClearText = onClickClearEmail,
            isInputValid = isEmailValid,
            messageError = emailError
        )

        VerticalSpacer32Dp()
        AuthButtonForm(
            title = stringResource(id = R.string.recover),
            onClickButton = onClickRecoverButton,
            isEnabled = isEnabled,
            isSubmit = isSubmit
        )
    }
}