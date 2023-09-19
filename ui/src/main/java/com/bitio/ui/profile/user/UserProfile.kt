package com.bitio.ui.profile.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.ProfileCustomTextField
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.theme.Porcelain

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickSaveButton: (String, String, String) -> Unit,
    onFullNameChange: (String) -> Unit,
    fullName: String,
    onPhoneNumberChange: (String) -> Unit,
    phoneNumber: String,
    onEmailChange: (String) -> Unit,
    email: String
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arraw_back),
                contentDescription = "back",
                tint = Porcelain,
            )
        }

        VerticalSpacer32Dp()

        Text(
            text = stringResource(id = R.string.profile_edit),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        VerticalSpacer32Dp()

        ProfileCustomTextField(
            value = fullName,
            placeholder = stringResource(id = R.string.full_name),
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = onFullNameChange,
            emailError = "",
        )

        ProfileCustomTextField(
            value = phoneNumber,
            placeholder = stringResource(id = R.string.phoneNumber),
            leadingIcon = painterResource(id = R.drawable.call),
            keyboardType = KeyboardType.Phone,
            onValueChange = onPhoneNumberChange,
            emailError = "",
        )

        ProfileCustomTextField(
            value = email,
            placeholder = stringResource(id = R.string.email),
            leadingIcon = painterResource(id = R.drawable.email),
            keyboardType = KeyboardType.Email,
            onValueChange = onEmailChange,
            emailError = "",
        )

        CustomButtonForm(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 22.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
            title = stringResource(id = R.string.save),
            onClickButton = {
                onClickSaveButton(fullName, phoneNumber, email)
            },
        )

    }
}
