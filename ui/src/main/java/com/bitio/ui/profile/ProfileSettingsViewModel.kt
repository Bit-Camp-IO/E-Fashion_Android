package com.bitio.ui.profile


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.usecase.profile.GetProfileSettingsUseCase
import com.bitio.usercomponent.domain.usecase.profile.SaveProfileSettingsUseCase
import com.bitio.utils.APP_TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class ProfileSettingsViewModel(
    private val saveProfileSettingsUseCase: SaveProfileSettingsUseCase,
    private val getProfileSettingsUseCase: GetProfileSettingsUseCase
) : ViewModel() {

    private val _profileSettingsUiState = MutableStateFlow(ProfileSettingsUiState())
    val profileSettingsUiState = _profileSettingsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getProfileSettingsUseCase().collect {
                _profileSettingsUiState.value = ProfileSettingsUiState(
                    darkModeEnabled = it.darkModeEnabled,
                    language = it.language
                )
            }
        }
    }

    internal fun onSwitchTheme(darkModeEnabled: Boolean) {
        viewModelScope.launch {
            saveProfileSettingsUseCase(
                ProfileSettingsUiState(
                    darkModeEnabled = darkModeEnabled,
                    language = _profileSettingsUiState.value.language
                )
            )
        }
    }

    internal fun onChangeLanguage(language: String) {
        viewModelScope.launch {
            saveProfileSettingsUseCase(
                ProfileSettingsUiState(
                    darkModeEnabled = _profileSettingsUiState.value.darkModeEnabled,
                    language = language
                )
            )
        }
    }

}