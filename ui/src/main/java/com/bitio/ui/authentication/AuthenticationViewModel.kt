package com.bitio.ui.authentication

import android.util.Log
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

    fun loginUser(fullName: String, password: String) {
        viewModelScope.launch {
            loginUseCase(fullName, password)
            val token = getAccessTokenUseCase()
            Log.d("zzz", token)
        }
    }

}