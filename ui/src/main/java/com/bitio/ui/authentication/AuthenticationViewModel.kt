package com.bitio.ui.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.LoginUseCase
import com.bitio.authcomponent.domain.useCases.RegisterUseCase
import kotlinx.coroutines.launch


class AuthenticationViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    val fullName = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirmation = mutableStateOf("")

    fun loginUser() {
        viewModelScope.launch {
            loginUseCase(email.value, password.value)
            val token = getAccessTokenUseCase()
            Log.d("zzz", token)
        }
    }

}