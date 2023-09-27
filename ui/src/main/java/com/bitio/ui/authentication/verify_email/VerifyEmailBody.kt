package com.bitio.ui.authentication.verify_email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.authentication.composable.AuthButtonForm
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp

@Composable
internal fun VerifyEmailBody(
    modifier: Modifier = Modifier,
    email: String,
    onValueChange: (String) -> Unit,
    onClickResetButton: () -> Unit,
    isEnabled: Boolean,
    isSubmit: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.verify_email),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(horizontal = 24.dp, vertical = 40.dp)
        )
        Text(
            text = stringResource(id = R.string.details_of_verify_email),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary
        )

        VerticalSpacer40Dp()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
            )
            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
            )
            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
            )
            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
            )
            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
            )
            VerifyEmailTextField(
                value = email,
                onValueChange = onValueChange,
                imeAction = ImeAction.Done
            )
        }

        VerticalSpacer32Dp()

        AuthButtonForm(
            title = stringResource(id = R.string.verify),
            onClickButton = onClickResetButton,
            isEnabled = isEnabled,
            isSubmit = isSubmit
        )
    }
}

@Composable
private fun VerifyEmailTextField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        modifier = Modifier
            .size(40.dp), value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        )
    )
}
