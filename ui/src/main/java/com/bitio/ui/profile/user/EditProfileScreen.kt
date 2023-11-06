package com.bitio.ui.profile.user

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.ProfileCustomTextField
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.Porcelain
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.utils.RealPathUtil
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.io.IOException

@Composable
fun EditProfileScreen(navController: NavController) {
    val userViewModel = getViewModel<UserViewModel>()
    val state by userViewModel.userProfileUiState

    EditProfileContent(
        profileUi = state.profileUi,
        onBackButtonClick = navController::navigateUp,
        onSaveButtonClicked = {
//            userViewModel.updateUserInfo(
//                UserUiState(
//                    fullName = userViewModel.fullName.value,
//                    email = userViewModel.email.value,
//                    phoneNumber = userViewModel.phoneNumber.value
//                )
//            )
        },
        onFullNameChange = {
            userViewModel.fullName.value = it
        },
        fullName = userViewModel.fullName.value,
        onEmailChange = {
            userViewModel.email.value = it
        },
        email = userViewModel.email.value,
        onPhoneNumberChange = {
            userViewModel.phoneNumber.value = it
        },
        phoneNumber = userViewModel.phoneNumber.value,
        onFullNameClearClicked = {
            userViewModel.fullName.value = ""
        },
        onEmailNameClearClicked = {
            userViewModel.email.value = ""

        }
    ) {
        userViewModel.phoneNumber.value = ""

    }
}

@Composable
private fun EditProfileContent(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    onFullNameChange: (String) -> Unit,
    fullName: String,
    email: String,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    phoneNumber: String,
    profileUi: UserProfile,
    onFullNameClearClicked: () -> Unit,
    onEmailNameClearClicked: () -> Unit,
    onPhoneNumberClearClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Header(
            painter = rememberAsyncImagePainter(model = profileUi.profileImage),
            fullName = profileUi.fullName
        )
        Body(onBackButtonClick = onBackButtonClick)
        Footer(
            fullName = fullName,
            onFullNameChange = onFullNameChange,
            email = email,
            onEmailChange = onEmailChange,
            phoneNumber = phoneNumber,
            onPhoneNumberChange = onPhoneNumberChange,
            onSaveButtonClicked = onSaveButtonClicked,
            onFullNameClearClicked = onFullNameClearClicked,
            onEmailNameClearClicked = onEmailNameClearClicked,
            onPhoneNumberClearClicked = onPhoneNumberClearClicked
        )
    }
}

@Composable
private fun Header(
    painter: Painter,
    fullName: String,
    modifier: Modifier = Modifier,
) {
  Box {
      Column(
          verticalArrangement = Arrangement.spacedBy(8.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = modifier
              .fillMaxWidth()
      ) {
          Image(
              painter = painter,
              contentDescription = null,
              modifier = Modifier
                  .size(120.dp)
                  .border(
                      width = 2.dp,
                      color = MaterialTheme.colorScheme.primary,
                      shape = RoundedCornerShape(100.dp)
                  )
                  .padding(4.dp)
                  .clip(RoundedCornerShape(100.dp)),
              contentScale = ContentScale.FillWidth,
          )
          VerticalSpacer8Dp()
          Text(
              text = fullName,
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onBackground
          )
      }
  }
}

@Composable
private fun Body(
    onBackButtonClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        IconButton(
            onClick = onBackButtonClick,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arraw_back),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(id = R.string.profile_edit),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Footer(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    onSaveButtonClicked: () -> Unit,
    onFullNameClearClicked: () -> Unit,
    onEmailNameClearClicked: () -> Unit,
    onPhoneNumberClearClicked: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileCustomTextField(
            value = fullName,
            placeholder = stringResource(id = R.string.full_name),
            leadingIcon = painterResource(id = R.drawable.profile),
            onValueChange = onFullNameChange,
            emailError = "",
            onClearTextClicked = onFullNameClearClicked
        )

        ProfileCustomTextField(
            value = email,
            placeholder = stringResource(id = R.string.email),
            leadingIcon = painterResource(id = R.drawable.email),
            onValueChange = onEmailChange,
            emailError = "",
            onClearTextClicked = onEmailNameClearClicked
        )
        ProfileCustomTextField(
            value = phoneNumber,
            placeholder = stringResource(id = R.string.phoneNumber),
            leadingIcon = painterResource(id = R.drawable.call),
            keyboardType = KeyboardType.Phone,
            onValueChange = onPhoneNumberChange,
            emailError = "",
            onClearTextClicked = onPhoneNumberClearClicked
        )
        VerticalSpacer32Dp()
        CustomButtonForm(
            title = stringResource(id = R.string.save),
            onClickButton = onSaveButtonClicked,
        )
    }
}

@Composable
private fun CustomEditIcon(
    modifier: Modifier = Modifier,
    onPermissionResult: (String, Boolean) -> Unit,
    onClickEditImage: (File) -> Unit,
) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                RealPathUtil.getRealPath(context, uri)?.let { path ->
                    val file = File(path)
                    onClickEditImage(file)
                }
            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val imagePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onPermissionResult(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                isGranted
            )
            if (isGranted) {
                galleryLauncher.launch("image/*")
            }
        }
    )

    IconButton(
        onClick = {
            imagePermissionResultLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        },
        modifier = modifier
            .offset(y = (-40).dp)
            .clip(RoundedCornerShape(100.dp))
            .size(24.dp)
            .background(MaterialTheme.colorScheme.secondary)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = "edit",
            tint = Porcelain
        )
    }
}