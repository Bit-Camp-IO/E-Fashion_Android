package com.bitio.ui.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.CheckIfUserLoggedInUseCase
import com.bitio.authcomponent.domain.useCases.LoginUseCase
import com.bitio.authcomponent.domain.useCases.RegisterUseCase
import com.bitio.authcomponent.domain.utils.ResponseStatus
import com.bitio.utils.APP_TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class AuthenticationViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val checkIfUserLoggedInUseCase: CheckIfUserLoggedInUseCase
) : ViewModel() {
    val fullName = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirmation = mutableStateOf("")

    private val _authUiState = mutableStateOf(AuthUiState())
    val authUiState = _authUiState


    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            checkIfUserLoggedInUseCase().collect {
                _isUserLoggedIn.value = it
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState(loading = true)
            when (val response = loginUseCase(email.value, password.value)) {
                is ResponseStatus.Error -> {
                    _authUiState.value =
                        AuthUiState(
                            loading = false,
                            errorMessage = response.errorMessage
                        )
                }

                is ResponseStatus.Success -> {
                    _authUiState.value =
                        AuthUiState(
                            loading = false,
                            state = response.data
                        )
                }
            }
        }
    }

    fun signUpUser() {
        viewModelScope.launch {
            viewModelScope.launch {
                _authUiState.value = AuthUiState(loading = true)
                when (val response = registerUseCase(fullName.value, email.value, password.value)) {
                    is ResponseStatus.Error -> {
                        _authUiState.value =
                            AuthUiState(
                                loading = false,
                                errorMessage = response.errorMessage
                            )
                    }

                    is ResponseStatus.Success -> {
                        _authUiState.value =
                            AuthUiState(
                                loading = false,
                                state = response.data
                            )
                    }
                }
            }
        }
    }
}