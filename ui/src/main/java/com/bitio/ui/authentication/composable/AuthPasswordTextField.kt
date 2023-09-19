package com.bitio.ui.authentication.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bitio.ui.R

@Composable
fun AuthPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    placeholder: String = stringResource(id = R.string.password),
    isPasswordValid: Boolean,
    passwordError: String
) {

    var isShowPassword by remember { mutableStateOf(false) }
    val trailingIcon = if (isShowPassword)
        painterResource(id = R.drawable.hide_eye)
    else
        painterResource(id = R.drawable.eye)

    OutlinedTextField(
        isError = isPasswordValid,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.password),
                contentDescription = null,
                tint = Color.Gray
            )
        },
        trailingIcon = {
            IconButton(
                { isShowPassword = !isShowPassword },
                enabled = value.isNotEmpty()
            ) {
                if (value.isNotEmpty()) {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = {
            if (value.isNotEmpty() && isPasswordValid) {
                Text(
                    text = passwordError,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red
                )
            }
        }
    )
}