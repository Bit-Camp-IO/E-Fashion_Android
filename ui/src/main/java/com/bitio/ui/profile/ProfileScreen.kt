package com.bitio.ui.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.profile.composable.SettingApp
import com.bitio.ui.profile.composable.UserProfile
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles
import com.bitio.utils.profileShape
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(

    isDarkTheme: Boolean,
    onSwitchTheme: () -> Unit
) {
    val viewModel = getViewModel<ProfileViewModel>()
    val state by viewModel.profileUiState.collectAsState()
    ProfileContent(state, isDarkTheme, onSwitchTheme)
}

@Composable
private fun ProfileContent(state: ProfileUiState, isDarkTheme: Boolean, onSwitchTheme: () -> Unit) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var isUserProfileVisible by remember {
        mutableStateOf(false)
    }

    val offsetXOfProfileSettings by animateDpAsState(
        targetValue = if (isUserProfileVisible) screenWidth else 0.dp, label = ""
    )
    val offsetXOfUserProfile by animateDpAsState(
        targetValue = if (isUserProfileVisible) 0.dp else -screenWidth, label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        CustomBlurProfileImage(
            image = state.profile.image,
            contentDescription = state.profile.username
        )

        Column(
            modifier = Modifier
                .padding(top = 64.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                CustomCircleProfileImage(
                    state.profile.image,
                    state.profile.username,
                )

                if (isUserProfileVisible) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .clip(RoundedCornerShape(100.dp))
                            .size(24.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "back",
                            tint = Porcelain
                        )
                    }
                }

            }

            VerticalSpacer8Dp()

            Text(
                text = state.profile.username,
                style = AppThemeTextStyles(Porcelain).titleMedium
            )

            VerticalSpacer16Dp()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(profileShape)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                SettingApp(
                    isDarkTheme = isDarkTheme,
                    onSwitchTheme = onSwitchTheme,
                    modifier = Modifier
                        .offset(x = offsetXOfProfileSettings)
                ) {
                    isUserProfileVisible = true
                }

                UserProfile(
                    onClickBack = {
                        isUserProfileVisible = false
                    },
                    modifier = Modifier
                        .offset(x = offsetXOfUserProfile),
                    onClickSaveButton = { username, phone, email ->
                        println("User info: $username $phone $email")
                    }
                )
            }
        }
    }
}


@Composable
private fun CustomBlurProfileImage(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxSize()
            .blur(radius = 30.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun CustomCircleProfileImage(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {

    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = contentDescription,
        modifier = modifier
            .size(120.dp)
            .clip(RoundedCornerShape(100.dp)),
        contentScale = ContentScale.FillBounds,
    )
}
