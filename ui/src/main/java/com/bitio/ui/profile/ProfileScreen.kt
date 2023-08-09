package com.bitio.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.profile.composable.ProfileSettings
import com.bitio.ui.profile.composable.Title
import com.bitio.ui.profile.composable.ProfileUser
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles
import kotlinx.coroutines.delay

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
    val screenHeight = configuration.screenHeightDp.dp
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

    ConstraintLayout(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        val (
            blurProfileImage,
            circleProfileImage,
            username,
            profileSettings,
            profileUser,
        ) = createRefs()

        BlurProfileImage(
            state.profile.image,
            state.profile.username,
            Modifier
                .fillMaxSize()
                .constrainAs(blurProfileImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        Box(
            Modifier
                .constrainAs(circleProfileImage) {
                    top.linkTo(blurProfileImage.top, margin = 64.dp)
                    start.linkTo(blurProfileImage.start)
                    end.linkTo(blurProfileImage.end)
                }
        ) {
            CircleProfileImage(
                state.profile.image,
                state.profile.username,
            )
            AnimatedVisibility(
                visible = isShowUserInfo,
                enter = fadeIn(animationSpec = tween(durationMillis = 100)),
                exit = fadeOut(animationSpec = tween(durationMillis = 100)),
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .offset(x = 100.dp, y = 16.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "back",
                        tint = Porcelain
                    )
                }
            }
        }

        Title(
            state.profile.username,
            Modifier
                .constrainAs(username) {
                    top.linkTo(circleProfileImage.bottom, margin = 8.dp)
                    start.linkTo(circleProfileImage.start)
                    end.linkTo(circleProfileImage.end)
                },
            style = AppThemeTextStyles(Color.Blue).titleMedium
        )

        ProfileSettings(
            isDarkTheme = isDarkTheme,
            onSwitchTheme = onSwitchTheme,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = offsetSettings)
                .constrainAs(profileSettings) {
                    top.linkTo(username.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
        ) {
            isShowUserInfo = !isShowUserInfo
        }

        ProfileUser(
            onClickBack = {},
            modifier = Modifier
                .offset(x = offsetUserInfo)
                .fillMaxSize()
                .constrainAs(profileUser) {
                    top.linkTo(username.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onClickSaveButton = { username, phone, email ->

            }
        )
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