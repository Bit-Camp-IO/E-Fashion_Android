package com.bitio.ui.authentication.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.CustomTextField
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp

@Composable
fun UserLogin(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickLoginButton: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickSignUp: () -> Unit,
    onClickCheckedBox: (Boolean) -> Unit,
    onClickClearEmail: () -> Unit,
    isLoading: Boolean
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(horizontal = 24.dp, vertical = 40.dp)
        )

        VerticalSpacer40Dp()

        CustomTextField(
            value = email,
            placeholder = stringResource(id = R.string.email),
            leadingIcon = painterResource(id = R.drawable.email),
            onValueChange = onEmailChange,
            onClickClearText = onClickClearEmail
        )
        VerticalSpacer16Dp()
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            imeAction = ImeAction.Done
        )
        VerticalSpacer16Dp()
        CheckBoxContainer(
            onClickCheckedBox = onClickCheckedBox,
            onClickForgetPassword = onClickForgetPassword
        )
        VerticalSpacer32Dp()
        CustomButtonForm(
            title = stringResource(id = R.string.login),
            onClickButton = onClickLoginButton,
            isLoading = isLoading
        )
        VerticalSpacer32Dp()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.do_you_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            TextButton(onClick = onClickSignUp) {
                Text(
                    text = stringResource(id = R.string.sign_up),
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
    onClickForgetPassword: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
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
            text = stringResource(id = R.string.remember_me),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onClickForgetPassword() }
                .padding(4.dp)
        )
    }
}
