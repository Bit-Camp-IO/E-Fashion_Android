package com.bitio.ui.authentication.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.CustomTextField
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun CustomLogin(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    password: MutableState<String>,
    onClickLoginButton: (String, String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickSignUp: () -> Unit
) {

    var isShowPassword by remember { mutableStateOf(false) }
    val iconPassword =
        if (isShowPassword) painterResource(id = R.drawable.hide_eye) else painterResource(
            id = R.drawable.eye
        )

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        VerticalSpacer64Dp()
        Text(
            text = "Log in",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).titleLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        VerticalSpacer32Dp()

        CustomTextField(
            value = email.value,
            placeholder = "Username",
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = { email.value = it },
        )

        CustomTextFieldPassword(
            value = password.value,
            leadingIcon = painterResource(id = R.drawable.password),
            trailingIcon = iconPassword,
            keyboardType = KeyboardType.Password,
            isShowPassword = isShowPassword,
            onValueChange = { password.value = it }
        ) { isShowPassword = !isShowPassword }

        CustomCheckBox(
            onCheckedChange = onCheckedChange,
            onClickForgetPassword = onClickForgetPassword
        )

        CustomButtonForm(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp, horizontal = 24.dp),
            title = "Log in"
        ) {
            onClickLoginButton(email.value, password.value)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Don't have an account ?",
                style = AppThemeTextStyles(Color.Gray).bodyMedium,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Sign up",
                style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp)
                    .clickable { onClickSignUp() }
            )
        }
    }
}

@Composable
private fun CustomCheckBox(
    onCheckedChange: (Boolean) -> Unit,
    onClickForgetPassword: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
                isChecked = it
            },
            colors = colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = "Remember me",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Forget Password",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).bodyMedium,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onClickForgetPassword() }
                .padding(4.dp)
        )
    }
}
