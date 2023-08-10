package com.bitio.ui.profile.composable

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.theme.PeacockBlue
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun ProfileUser(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickSaveButton: (String, String, String) -> Unit
) {
    ProfileUserContent(
        modifier = modifier,
        onClickBack = onClickBack,
        onClickSaveButton = onClickSaveButton
    )
}

@Composable
private fun ProfileUserContent(
    modifier: Modifier,
    onClickBack: () -> Unit,
    onClickSaveButton: (String, String, String) -> Unit
) {

    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .drawBehind {},
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

        TextFieldContainer(
            value = username,
            label = "Sajjad Abdel Aziz",
            painter = painterResource(id = R.drawable.profile)
        ) {
            username = it
        }

        TextFieldContainer(
            value = phoneNumber,
            label = "+964",
            painter = painterResource(id = R.drawable.call),
            keyboardType = KeyboardType.Phone,
        ) {
            phoneNumber = it
        }

        TextFieldContainer(
            value = email,
            label = "example@gmail.com",
            painter = painterResource(id = R.drawable.email),
            keyboardType = KeyboardType.Email
        ) {
            email = it
        }


        Button(
            onClick = {
                onClickSaveButton(username, phoneNumber, email)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 22.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            colors = buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Title(
                text = "Save",
                style = AppThemeTextStyles(Color.White).titleMedium,
                modifier = Modifier
            )
        }

    }
}

@Composable
private fun TextFieldContainer(
    value: String,
    label: String,
    painter: Painter,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp),
        value = value, onValueChange = onValueChange,
        textStyle = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).bodySmall,
        singleLine = true,
        maxLines = 1,
        label = {
            Title(
                text = label,
                style = AppThemeTextStyles(Color.Gray).titleSmall
            )
        },
        leadingIcon = {
            Icon(
                painter = painter,
                contentDescription = "",
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
        ),
    )
}
