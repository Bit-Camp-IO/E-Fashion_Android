package com.bitio.ui.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.usecase.profile.GetProfileSettingsUseCase
import com.bitio.usercomponent.domain.usecase.profile.SaveProfileSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class ProfileSettingsViewModel(
    private val saveProfileSettingsUseCase: SaveProfileSettingsUseCase,
    private val getProfileSettingsUseCase: GetProfileSettingsUseCase
) : ViewModel() {

    private val _profileSettingsUiState = MutableStateFlow(LocalProfileSettingsUiState())
    val profileSettingsUiState = _profileSettingsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getProfileSettingsUseCase().collect {
                _profileSettingsUiState.value = LocalProfileSettingsUiState(
                    darkModeEnabled = it.darkModeEnabled,
                    language = it.language
                )
            }
        }
    }

    internal fun onSwitchTheme(darkModeEnabled: Boolean) {
        viewModelScope.launch {
            saveProfileSettingsUseCase(
                LocalProfileSettingsUiState(
                    darkModeEnabled = darkModeEnabled,
                    language = _profileSettingsUiState.value.language
                )
            )
        }
    }

    internal fun onChangeLanguage(language: String) {
        viewModelScope.launch {
            saveProfileSettingsUseCase(
                LocalProfileSettingsUiState(
                    darkModeEnabled = _profileSettingsUiState.value.darkModeEnabled,
                    language = language
                )
            )
        }
    }

}