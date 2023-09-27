package com.bitio.ui.authentication.verify_email

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.authentication.AuthUiState
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.route.navigateToResetPasswordScreen
import com.bitio.ui.shared.shapeOfAuthForm
import com.bitio.ui.shared.shapeOfImageProfile
import org.koin.androidx.compose.getViewModel

@Composable
fun VerifyEmailScreen(navController: NavController) {
    val viewModel = getViewModel<AuthenticationViewModel>()
    val authState by viewModel.authUiState
    val authEventState by viewModel.validationAuthenticationEventsUiState.collectAsState()

    if (!authState.loading && authState.errorMessage.isEmpty()) {
        navController::navigateToResetPasswordScreen
    }

    VerifyContent(authState = authState)
}

@Composable
private fun VerifyContent(
    authState: AuthUiState
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val scrollState = rememberScrollState()
    val context = LocalContext.current


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
                VerifyEmailBody(
                    email = "",
                    onValueChange = {},
                    onClickResetButton = { /*TODO*/ },
                    isEnabled = true,
                    isSubmit = true
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
