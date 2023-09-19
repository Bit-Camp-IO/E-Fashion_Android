package com.bitio.ui.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.authcomponent.domain.useCases.auth.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.auth.CheckIfUserLoggedInUseCase
import com.bitio.authcomponent.domain.useCases.auth.LoginUseCase
import com.bitio.authcomponent.domain.useCases.auth.RegisterUseCase
import com.bitio.authcomponent.domain.utils.ResponseStatus
import com.bitio.authcomponent.domain.useCases.validate.ValidateConfirmPasswordUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateEmailUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateFullNameUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidatePasswordUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateTermsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class AuthenticationViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val checkIfUserLoggedInUseCase: CheckIfUserLoggedInUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateTermsUseCase: ValidateTermsUseCase
) : ViewModel() {


    private val _checkValidationEmailStream = MutableStateFlow(false)
    val email = _checkValidationEmailStream.asStateFlow()

    private val _authUiState = mutableStateOf(AuthUiState())
    val authUiState = _authUiState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private var _validationAuthenticationEventsUiState = MutableStateFlow(AuthenticationFormState())
    val validationAuthenticationEventsUiState = _validationAuthenticationEventsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            checkIfUserLoggedInUseCase().collect {
                _isUserLoggedIn.value = it
            }
        }
    }

    fun onLoginEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        email = event.email
                    )
                }
                val emailResult =
                    validateEmailUseCase(_validationAuthenticationEventsUiState.value.email)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        emailError = emailResult.error,
                    )
                }
            }

            is LoginFormEvent.PasswordChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        password = event.password
                    )
                }
                val passwordResult =
                    validatePasswordUseCase(_validationAuthenticationEventsUiState.value.password)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        passwordError = passwordResult.error,
                    )
                }
            }

            is LoginFormEvent.CheckRememberMe -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        checkRememberMe = event.isChecked
                    )
                }
            }

            LoginFormEvent.LogIn -> {
                loginUser()
            }
        }
    }

    fun onSignUpEvent(event: SignupFormEvent) {
        when (event) {

            is SignupFormEvent.FulNameChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        fullName = event.fullName
                    )
                }
                val fullNameResult =
                    validateFullNameUseCase(_validationAuthenticationEventsUiState.value.fullName)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        fullNameError = fullNameResult.error,
                    )
                }
            }

            is SignupFormEvent.EmailChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        email = event.email
                    )
                }
                val emailResult =
                    validateEmailUseCase(_validationAuthenticationEventsUiState.value.email)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        emailError = emailResult.error,
                    )
                }
            }

            is SignupFormEvent.PasswordChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        password = event.password
                    )
                }
                val passwordResult =
                    validatePasswordUseCase(_validationAuthenticationEventsUiState.value.password)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        passwordError = passwordResult.error,
                    )
                }
            }

            is SignupFormEvent.ConfirmPasswordChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
                val confirmPasswordResult =
                    validateConfirmPasswordUseCase(
                        _validationAuthenticationEventsUiState.value.password,
                        _validationAuthenticationEventsUiState.value.confirmPassword,
                    )
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        confirmPasswordError = confirmPasswordResult.error,
                    )
                }
            }

            is SignupFormEvent.AcceptTerms -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        acceptedTerms = event.isAccepted
                    )
                }
                val termResult =
                    validateTermsUseCase(_validationAuthenticationEventsUiState.value.acceptedTerms)
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        termsError = termResult.error,
                    )
                }
            }

            is SignupFormEvent.SignUp -> {
                signUpUser()
            }
        }
    }

//    private fun checkLoginData() {
//        val emailResult = validateEmailUseCase(_validationAuthenticationEventsUiState.value.email)
//        val passwordResult =
//            validatePasswordUseCase(_validationAuthenticationEventsUiState.value.password)
//
//        val hasError = listOf(
//            emailResult,
//            passwordResult,
//        ).any {
//            !it.successful
//        }
//
//        if (hasError) {
//            _validationAuthenticationEventsUiState.update {
//                it.copy(
//                    emailError = emailResult.error,
//                    passwordError = passwordResult.error,
//                )
//            }
//            return
//        }
//    }

    private fun loginUser() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState(loading = true)
            when (val response = loginUseCase(
                _validationAuthenticationEventsUiState.value.email,
                _validationAuthenticationEventsUiState.value.password
            )) {
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


    private fun signUpUser() {
        viewModelScope.launch {
            viewModelScope.launch {
                _authUiState.value = AuthUiState(loading = true)
                when (val response = registerUseCase(
                    _validationAuthenticationEventsUiState.value.fullName,
                    _validationAuthenticationEventsUiState.value.email,
                    _validationAuthenticationEventsUiState.value.password
                )) {
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