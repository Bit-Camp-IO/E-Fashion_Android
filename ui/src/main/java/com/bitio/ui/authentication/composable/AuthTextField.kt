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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R

@Composable
internal fun AuthTextField(
    value: String,
    placeholder: String,
    leadingIcon: Painter,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    onClickClearText: () -> Unit = {},
    isInputValid: Boolean,
    messageError: String,
) {

    OutlinedTextField(
        isError = isInputValid,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onClickClearText,
                enabled = value.isNotEmpty()
            ) {
                if (value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        supportingText = {
            if (value.isNotEmpty() && isInputValid) {
                Text(
                    text = messageError,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red
                )
            }
        }
    )
}
