package com.bitio.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.profile.composable.ProfileSettings
import com.bitio.ui.profile.composable.Title
import com.bitio.ui.profile.composable.ProfileUser
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    onSwitchTheme: () -> Unit
) {
    val state by viewModel.profileUiState.collectAsState()
    ProfileContent(state, isDarkTheme, onSwitchTheme)
}

@Composable
private fun ProfileContent(state: ProfileUiState, isDarkTheme: Boolean, onSwitchTheme: () -> Unit) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var isShowUserInfo by remember {
        mutableStateOf(false)
    }
    val offsetSettings by animateDpAsState(
        targetValue = if (isShowUserInfo) screenWidth else 0.dp, label = ""
    )
    val offsetUserInfo by animateDpAsState(
        targetValue = if (isShowUserInfo) 0.dp else -screenWidth, label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        BlurProfileImage(
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
                CircleProfileImage(
                    state.profile.image,
                    state.profile.username,
                )

                if (isShowUserInfo) {
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProfileSettings(
                        isDarkTheme = isDarkTheme,
                        onSwitchTheme = onSwitchTheme,
                        modifier = Modifier
                            .offset(x = offsetSettings)
                    ) {
                        isShowUserInfo = !isShowUserInfo
                    }

                    ProfileUser(
                        onClickBack = {
                            isShowUserInfo = !isShowUserInfo
                        },
                        modifier = Modifier
                            .offset(x = offsetUserInfo),
                        onClickSaveButton = { username, phone, email ->
                            println("User info: $username $phone $email")
                        }
                    )
                }
            }

        }
    }
}


@Composable
private fun BlurProfileImage(
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
private fun CircleProfileImage(
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