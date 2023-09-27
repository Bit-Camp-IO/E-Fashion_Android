package com.bitio.ui.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.authcomponent.domain.useCases.auth.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.auth.CheckIfUserLoggedInUseCase
import com.bitio.authcomponent.domain.useCases.auth.ForgotPasswordUseCase
import com.bitio.authcomponent.domain.useCases.auth.LoginUseCase
import com.bitio.authcomponent.domain.useCases.auth.ResetPasswordUseCase
import com.bitio.authcomponent.domain.useCases.auth.SignUpUseCase
import com.bitio.authcomponent.domain.useCases.auth.VerifyEmailUseCase
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
    private val signUpUseCase: SignUpUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val checkIfUserLoggedInUseCase: CheckIfUserLoggedInUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateTermsUseCase: ValidateTermsUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase
) : ViewModel() {


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

    fun onAuthEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.EmailChanged -> {
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

            is AuthFormEvent.PasswordChanged -> {
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

            is AuthFormEvent.CheckRememberMe -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        checkRememberMe = event.isChecked
                    )
                }
            }

            AuthFormEvent.LogIn -> {
                loginUser()
            }

            is AuthFormEvent.FulNameChanged -> {
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


            is AuthFormEvent.ConfirmPasswordChanged -> {
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

            is AuthFormEvent.AcceptTerms -> {
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

            is AuthFormEvent.SignUp -> {
                signUpUser()
            }


            is AuthFormEvent.VerifyEmailChanged -> {
                _validationAuthenticationEventsUiState.update {
                    it.copy(
                        otp = event.otp
                    )
                }
            }
            AuthFormEvent.Reset -> {
                resetPassword()
            }

            AuthFormEvent.Recover -> {
                forgotPassword()
            }

            AuthFormEvent.Verify -> {
                verifyEmail()
            }
        }
    }


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
                when (val response = signUpUseCase(
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

    private fun resetPassword() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState(loading = true)
            when (val response = resetPasswordUseCase(
                _validationAuthenticationEventsUiState.value.email,
                _validationAuthenticationEventsUiState.value.password,
                _validationAuthenticationEventsUiState.value.otp,
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

    private fun forgotPassword() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState(loading = true)
            when (val response = forgotPasswordUseCase(
                _validationAuthenticationEventsUiState.value.email,
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

    private fun verifyEmail() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState(loading = true)
            when (val response = verifyEmailUseCase(
                _validationAuthenticationEventsUiState.value.otp,
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