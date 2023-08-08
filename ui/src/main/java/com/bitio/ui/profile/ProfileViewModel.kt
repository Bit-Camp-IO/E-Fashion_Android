package com.bitio.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor():ViewModel(){

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState : StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

}