package com.bitio.ui.authentication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    val authViewModel = getViewModel<AuthenticationViewModel>()
    Log.d("TAG", "HashCodeOfAuthViewModel ForgotPasswordScreen: ${authViewModel.hashCode()}")

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forgot password", Modifier.clickable { navController.popBackStack() })
    }
}