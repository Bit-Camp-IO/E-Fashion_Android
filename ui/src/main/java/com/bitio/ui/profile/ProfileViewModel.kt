package com.bitio.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.GetUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.AddAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.AddUserImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAddressesOfUseCase: GetAddressesOfUseCase,
    private val addAddressOfUserUseCase: AddAddressOfUserUseCase,
    private val deleteAddressOfUserUseCase: DeleteAddressOfUserUseCase,
    private val addUserImageUseCase: AddUserImageUseCase
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val response = getUserInfoUseCase()) {
                is ResponseStatus.Success -> {
                    Log.d("ProfileViewModel", "Success getUserInfoUseCase: ${response.data}")
                }

                is ResponseStatus.Error -> {
                    Log.d("ProfileViewModel", "Error getUserInfoUseCase: ${response.errorMessage}")
                }
            }
        }
    }

}