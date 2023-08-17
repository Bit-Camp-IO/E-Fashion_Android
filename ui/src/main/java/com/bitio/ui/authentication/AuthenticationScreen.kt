package com.bitio.ui.authentication

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.authentication.route.navigateToForgotPasswordScreen
import com.bitio.ui.authentication.composable.CustomLogin
import com.bitio.ui.authentication.composable.CustomSignUp
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens
import com.bitio.utils.profileShape
import kotlinx.coroutines.flow.update


@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AuthenticationContent(
            onClickForgetPassword = navController::navigateToForgotPasswordScreen,
            onClickLoginButton = {
                viewModel.checkIfLogin.value = true
                navController.navigate(route = HomeRouteScreens.Home.route)
            }
        )
    }
}

@Composable
private fun AuthenticationContent(
    onClickForgetPassword: () -> Unit,
    onClickLoginButton: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var isClickOnSignUp by remember {
        mutableStateOf(false)
    }

    val offsetXOfLogin by animateDpAsState(
        targetValue = if (isClickOnSignUp) screenWidth else 0.dp, label = ""
    )
    val offsetXOfSignUp by animateDpAsState(
        targetValue = if (isClickOnSignUp) 0.dp else -screenWidth, label = ""
    )


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://th.bing.com/th/id/R.5348dea5f236c678d5ac16487d0f8369?rik=sNOQgCVK6kF8%2fA&riu=http%3a%2f%2fimages.unsplash.com%2fphoto-1529665253569-6d01c0eaf7b6%3fixlib%3drb-1.2.1%26q%3d80%26fm%3djpg%26crop%3dentropy%26cs%3dtinysrgb%26w%3d1080%26fit%3dmax%26ixid%3deyJhcHBfaWQiOjEyMDd9&ehk=ehAiI047tOUe6tNkdDeZmOuZcCO0hKC030nagdxXcss%3d&risl=&pid=ImgRaw&r=0"),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.authentication_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(profileShape)
                    .background(MaterialTheme.colorScheme.background)
            ) {

                CustomLogin(
                    modifier = Modifier.offset(x = offsetXOfLogin),
                    onClickLoginButton = { username, password ->
                        onClickLoginButton()
                    },
                    onCheckedChange = {},
                    onClickForgetPassword = onClickForgetPassword
                ) {
                    isClickOnSignUp = true
                }

                CustomSignUp(modifier = Modifier.offset(x = offsetXOfSignUp),
                    onClickSignUpButton = { username, email, password ->
                        println("User info: $username $email $password")
                    },
                    onCheckedChange = {},
                    onClickPrivacy = {},
                    onClickPolicy = {}
                ) {
                    isClickOnSignUp = false
                }
            }

        }
    }
}

