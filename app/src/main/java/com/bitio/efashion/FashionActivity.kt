package com.bitio.efashion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.profile.ProfileSettingsViewModel
import com.bitio.ui.theme.EFashionTheme
import org.koin.android.ext.android.inject


class FashionActivity : ComponentActivity() {


    private val profileSettingsViewModel: ProfileSettingsViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by profileSettingsViewModel.profileSettingsUiState.collectAsState()
            val isUserLoggedIn by authenticationViewModel.isUserLoggedIn.collectAsState()

            Crossfade(
                targetState = state.darkModeEnabled,
                label = "",
                animationSpec = tween(
                    durationMillis = 650,
                    easing = FastOutSlowInEasing
                )
            ) { darkModeEnabled ->
                EFashionTheme(
                    darkTheme = darkModeEnabled
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        BottomNavigationBar(checkIfLogin = isUserLoggedIn)
                    }
                }
            }
        }
    }
}

