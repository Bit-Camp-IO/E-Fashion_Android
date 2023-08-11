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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CustomSignUp(
    modifier: Modifier = Modifier,
    onClickSignUpButton: (String, String, String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onClickPrivacy: () -> Unit,
    onClickPolicy: () -> Unit,
    onClickLogIn: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isShowPassword by remember { mutableStateOf(false) }
    val iconPassword =
        if (isShowPassword) painterResource(id = R.drawable.hide_eye) else painterResource(
            id = R.drawable.eye
        )

    Column(modifier = modifier.verticalScroll(state = rememberScrollState())) {

        VerticalSpacer64Dp()

        Text(
            text = "Sign up",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).titleLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        VerticalSpacer32Dp()


        CustomTextField(
            value = username,
            placeholder = "Username",
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = {
                username = it
            },
        )
        CustomTextField(
            value = email,
            placeholder = "Email",
            leadingIcon = painterResource(id = R.drawable.email),
            onValueChange = {
                email = it
            },
        )

        CustomTextFieldPassword(
            value = password,
            leadingIcon = painterResource(id = R.drawable.password),
            trailingIcon = iconPassword,
            keyboardType = KeyboardType.Password,
            isShowPassword = isShowPassword,
            onValueChange = { password = it }
        ) { isShowPassword = !isShowPassword }

        CustomCheckBox(onCheckedChange, onClickPrivacy, onClickPolicy)

        CustomButtonForm(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp, horizontal = 24.dp),
            title = "Sign up"
        ) {
            onClickSignUpButton(username, email, password)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Already have an account ?",
                style = AppThemeTextStyles(Color.Gray).bodyMedium,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Log in",
                style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { onClickLogIn() }
                    .padding(4.dp)
            )
        }
    }
}



@Composable
private fun CustomCheckBox(
    onCheckedChange: (Boolean) -> Unit,
    onClickPrivacy: () -> Unit,
    onClickPolicy: () -> Unit,
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
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = "I Agree with ",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodyMedium,
        )
        Text(
            text = "privacy",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).bodyMedium,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onClickPrivacy() }
                .padding(horizontal = 2.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "and",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodyMedium,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "policy",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.secondary).bodyMedium,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onClickPolicy() }
                .padding(horizontal = 2.dp)
        )
    }
}
