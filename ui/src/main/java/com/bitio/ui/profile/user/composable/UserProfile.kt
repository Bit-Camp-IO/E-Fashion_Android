package com.bitio.ui.profile.user.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.profile.user.ProfileUi
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.CustomTextField
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun UserProfile(
    profileUi: ProfileUi,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickSaveButton: (String, String, String) -> Unit,
) {
    var fullName by remember { mutableStateOf(profileUi.fullName) }
    var phoneNumber by remember { mutableStateOf(profileUi.phoneNumber) }
    var email by remember { mutableStateOf(profileUi.email) }

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
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
            text = "Edit Profile",
            style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        VerticalSpacer32Dp()

        CustomTextField(
            value = fullName,
            placeholder = "Username",
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = {
                fullName = it
            },
        )

        CustomTextField(
            value = phoneNumber,
            placeholder = "+964",
            leadingIcon = painterResource(id = R.drawable.call),
            keyboardType = KeyboardType.Phone,
            onValueChange = {
                phoneNumber = it
            },
        )

          CustomTextField(
            value = email,
            placeholder = "example@gmail.com",
            leadingIcon = painterResource(id = R.drawable.email),
            keyboardType = KeyboardType.Email,
            onValueChange = {
                email = it
            },
        )

        CustomButtonForm(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 22.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
            title = "Save"
        ) {
            onClickSaveButton(fullName, phoneNumber, email)
        }

    }
}
