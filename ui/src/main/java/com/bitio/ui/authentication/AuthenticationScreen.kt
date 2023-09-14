package com.bitio.ui.authentication

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.authentication.composable.UserLogin
import com.bitio.ui.authentication.composable.UserSignUp
import com.bitio.ui.authentication.route.navigateToForgotPasswordScreen
import com.bitio.ui.shared.shapeOfAuthForm
import com.bitio.ui.shared.shapeOfImageProfile
import org.koin.androidx.compose.getViewModel


@Composable
fun AuthenticationScreen(navController: NavController) {
    val viewModel = getViewModel<AuthenticationViewModel>()
    val state by viewModel.authUiState
    AuthenticationContent(
        state = state,
        fullName = viewModel.fullName.value,
        onFullNameChange = { viewModel.fullName.value = it },
        email = viewModel.email.value,
        onEmailChange = { viewModel.email.value = it },
        password = viewModel.password.value,
        onPasswordChange = { viewModel.password.value = it },
        onClickLoginButton = viewModel::loginUser,
        onClickSignUpButton = viewModel::signUpUser,
        onClickForgetPassword = navController::navigateToForgotPasswordScreen,
        onClickCheckedBox = {},
        onClickAgreeCheckedBox = {},
        onClickClearEmail = { viewModel.email.value = "" },
        onClickClearFullName = { viewModel.fullName.value = "" },
        onPasswordConfirmationChange = { viewModel.passwordConfirmation.value = it },
        passwordConfirmation = viewModel.passwordConfirmation.value
    )
}

@Composable
private fun AuthenticationContent(
    state: AuthUiState,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onClickLoginButton: () -> Unit,
    onClickSignUpButton: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickCheckedBox: (Boolean) -> Unit,
    onClickAgreeCheckedBox: (Boolean) -> Unit,
    onClickClearEmail: () -> Unit,
    onClickClearFullName: () -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    passwordConfirmation: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var isUserClickedOnSignUp by rememberSaveable {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .size(screenWidth, screenHeight)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Background(
            painter = rememberAsyncImagePainter(model = "https://th.bing.com/th/id/R.5348dea5f236c678d5ac16487d0f8369?rik=sNOQgCVK6kF8%2fA&riu=http%3a%2f%2fimages.unsplash.com%2fphoto-1529665253569-6d01c0eaf7b6%3fixlib%3drb-1.2.1%26q%3d80%26fm%3djpg%26crop%3dentropy%26cs%3dtinysrgb%26w%3d1080%26fit%3dmax%26ixid%3deyJhcHBfaWQiOjEyMDd9&ehk=ehAiI047tOUe6tNkdDeZmOuZcCO0hKC030nagdxXcss%3d&risl=&pid=ImgRaw&r=0"),
            modifier = Modifier
                .height(screenHeight / 1.5f)
                .graphicsLayer {
                    alpha = 1 - (3f * scrollState.value.toFloat() / scrollState.maxValue)
                }
        )

        Header(
            painter = rememberAsyncImagePainter(model = "https://th.bing.com/th/id/R.5348dea5f236c678d5ac16487d0f8369?rik=sNOQgCVK6kF8%2fA&riu=http%3a%2f%2fimages.unsplash.com%2fphoto-1529665253569-6d01c0eaf7b6%3fixlib%3drb-1.2.1%26q%3d80%26fm%3djpg%26crop%3dentropy%26cs%3dtinysrgb%26w%3d1080%26fit%3dmax%26ixid%3deyJhcHBfaWQiOjEyMDd9&ehk=ehAiI047tOUe6tNkdDeZmOuZcCO0hKC030nagdxXcss%3d&risl=&pid=ImgRaw&r=0"),
            modifier = Modifier.graphicsLayer {
                scaleX = 1 - (0.5 * (scrollState.value.toFloat() / scrollState.maxValue)).toFloat()
                scaleY = 1 - (0.5 * (scrollState.value.toFloat() / scrollState.maxValue)).toFloat()
                translationX = scrollState.value.toFloat()
                translationY = (scrollState.value.toFloat() / screenHeight.value)
            },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = screenHeight / 3.5f)
        ) {
            Body(
                fullName = fullName,
                onFullNameChange = onFullNameChange,
                email = email,
                onEmailChange = onEmailChange,
                password = password,
                onPasswordChange = onPasswordChange,
                isUserClickedOnSignUp = isUserClickedOnSignUp,
                onClickSignUp = {
                    isUserClickedOnSignUp = it
                },
                screenWidth = screenWidth,
                onClickLoginButton = onClickLoginButton,
                onClickForgetPassword = onClickForgetPassword,
                onClickCheckedBox = onClickCheckedBox,
                onClickLogIn = {
                    isUserClickedOnSignUp = it
                },
                onClickAgreeCheckedBox = onClickAgreeCheckedBox,
                onClickSignUpButton = onClickSignUpButton,
                onClickClearEmail = onClickClearEmail,
                onClickClearFullName = onClickClearFullName,
                state = state,
                onPasswordConfirmationChange = onPasswordConfirmationChange,
                passwordConfirmation = passwordConfirmation
            )
        }
    }
}

@Composable
private fun Background(
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .clip(shapeOfImageProfile)
            .fillMaxWidth()
            .blur(radius = 20.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun Header(
    painter: Painter,
    modifier: Modifier = Modifier,
) {

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .padding(top = 40.dp)
            .size(120.dp)
            .clip(RoundedCornerShape(100.dp)),
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
fun Body(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    passwordConfirmation: String,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    isUserClickedOnSignUp: Boolean,
    screenWidth: Dp,
    onClickLoginButton: () -> Unit,
    onClickSignUpButton: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickCheckedBox: (Boolean) -> Unit,
    onClickLogIn: (Boolean) -> Unit,
    onClickSignUp: (Boolean) -> Unit,
    onClickAgreeCheckedBox: (Boolean) -> Unit,
    onClickClearEmail: () -> Unit,
    onClickClearFullName: () -> Unit,
    state: AuthUiState,
) {
    val offsetXOfLogin by animateDpAsState(
        targetValue = if (isUserClickedOnSignUp) screenWidth else 0.dp, label = ""
    )
    val offsetXOfSignUp by animateDpAsState(
        targetValue = if (isUserClickedOnSignUp) 0.dp else -screenWidth, label = ""
    )


    Column(
        modifier = Modifier
            .wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            modifier = Modifier
                .clip(shapeOfAuthForm)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            UserLogin(
                modifier = Modifier.offset(x = offsetXOfLogin),
                email = email,
                password = password,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onClickLoginButton = onClickLoginButton,
                onClickForgetPassword = onClickForgetPassword,
                onClickSignUp = { onClickSignUp(true) },
                onClickCheckedBox = onClickCheckedBox,
                onClickClearEmail = onClickClearEmail,
                isLoading = state.loading
            )

            UserSignUp(
                modifier = Modifier.offset(x = offsetXOfSignUp),
                fullName = fullName,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation,
                onFullNameChange = onFullNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onPasswordConfirmationChange = onPasswordConfirmationChange,
                onClickSignUpButton = onClickSignUpButton,
                onClickLogIn = { onClickLogIn(false) },
                onClickAgreeCheckedBox = onClickAgreeCheckedBox,
                onClickClearFullName = onClickClearFullName,
                onClickClearEmail = onClickClearEmail,
                isLoading = state.loading,
            )
        }
    }
}



