package com.bitio.ui.authentication.reset_password

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.authcomponent.domain.useCases.validate.ValidForm
import com.bitio.ui.R
import com.bitio.ui.authentication.AuthFormEvent
import com.bitio.ui.authentication.AuthUiState
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.route.navigateToLoginScreen
import com.bitio.ui.shared.shapeOfAuthForm
import com.bitio.ui.shared.shapeOfImageProfile
import org.koin.androidx.compose.getViewModel

@Composable
fun ResetPasswordScreen(navController: NavController) {
    val viewModel = getViewModel<AuthenticationViewModel>()
    val authState by viewModel.authUiState
    val authEventState by viewModel.validationAuthenticationEventsUiState.collectAsState()

    if (!authState.loading && authState.errorMessage.isEmpty()) {
        navController::navigateToLoginScreen
    }

    val emailError = if (authEventState.emailError == ValidForm.UnValidEmail) stringResource(
        id = R.string.unvalid_email
    ) else ""

    val passwordError =
        if (authEventState.passwordError == ValidForm.UnValidPassword) stringResource(
            id = R.string.unvalid_password
        ) else ""

    ResetPasswordContent(
        email = authEventState.email,
        onEmailChange = { viewModel.onAuthEvent(AuthFormEvent.EmailChanged(it)) },
        onClickResetButton = { viewModel.onAuthEvent(AuthFormEvent.Reset) },
        onClickClearEmail = { viewModel.onAuthEvent(AuthFormEvent.EmailChanged("")) },
        isEmailValid = authEventState.emailError != ValidForm.ValidEmail,
        emailError = emailError,
        authState = authState,
        passwordError = passwordError,
        isPasswordValid = authEventState.passwordError != ValidForm.ValidPassword,
        onPasswordChange = { viewModel.onAuthEvent(AuthFormEvent.PasswordChanged(it)) },
        password = authEventState.password
    )
}

@Composable
private fun ResetPasswordContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickResetButton: () -> Unit,
    onClickClearEmail: () -> Unit,
    isPasswordValid: Boolean,
    isEmailValid: Boolean,
    authState: AuthUiState,
    passwordError: String,
    emailError: String,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val isEnabled = emailError.isEmpty() && passwordError.isEmpty()

    LaunchedEffect(key1 = authState.errorMessage) {
        authState.errorMessage.takeIf { it.isNotEmpty() }?.let {
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
            Box(
                modifier = Modifier
                    .clip(shapeOfAuthForm)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                ResetPasswordBody(
                    modifier = Modifier,
                    email = email,
                    password = password,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onClickClearEmail = onClickClearEmail,
                    isSubmit = authState.loading,
                    isPasswordValid = isPasswordValid,
                    passwordError = passwordError,
                    isEmailValid = isEmailValid,
                    emailError = emailError,
                    isEnabled = isEnabled,
                    onClickResetButton = onClickResetButton
                )
            }
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
