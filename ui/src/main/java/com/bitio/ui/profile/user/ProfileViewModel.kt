package com.bitio.ui.profile.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.GetUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.AddAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.AddUserImageUseCase
import com.bitio.usercomponent.domain.usecase.UpdateUserInfoUseCase
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getAddressesOfUseCase: GetAddressesOfUseCase,
    private val addAddressOfUserUseCase: AddAddressOfUserUseCase,
    private val deleteAddressOfUserUseCase: DeleteAddressOfUserUseCase,
    private val addUserImageUseCase: AddUserImageUseCase,
) : ViewModel() {

    private val _profileUiState = mutableStateOf(ProfileUiState())
    val profileUiState = _profileUiState


    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            _profileUiState.value = ProfileUiState(loading = true)
            when (val response = getUserInfoUseCase()) {
                is ResponseStatus.Success -> {
                    response.data?.let {
                        _profileUiState.value = ProfileUiState(
                            loading = false,
                            profileUi = ProfileUi(
                                fullName = it.fullName ?: "",
                                email = it.email ?: "",
                                phoneNumber = it.phoneNumber ?: "",
                                profileImage = it.profileImage ?: "",
                                settingsUi = SettingsUi(
                                    language = it.settings?.language ?: "",
                                    addresses = it.settings?.addresses.mapToAddressUi() ?: emptyList()
                                )
                            )
                        )
                    }
                }

                is ResponseStatus.Error -> {
                    _profileUiState.value = ProfileUiState(
                        loading = false,
                        errorMessage = response.errorMessage
                    )
                }
            }
        }
    }

    fun updateUserInfo(userUiState: UserUiState){
        viewModelScope.launch {
            _profileUiState.value = ProfileUiState(loading = true)
            when (val response =  updateUserInfoUseCase(userUiState)) {
                is ResponseStatus.Success -> {
                    response.data?.let {
                        _profileUiState.value = ProfileUiState(
                            loading = false,
                            profileUi = ProfileUi(
                                fullName = it.fullName ?: "",
                                phoneNumber = it.phoneNumber ?: "",
                                email = it.email ?: "",
                                profileImage = it.profileImage ?: "",
                                settingsUi = SettingsUi(
                                    language = it.settings?.language ?: "",
                                    addresses = it.settings?.addresses.mapToAddressUi()
                                        ?: emptyList()
                                )
                            )
                        )
                    }
                }

                is ResponseStatus.Error -> {
                    _profileUiState.value = ProfileUiState(
                        loading = false,
                        errorMessage = response.errorMessage
                    )
                }
            }
        }
    }

    private fun List<Address>?.mapToAddressUi(): List<AddressUi>? {
        return this?.map {
            AddressUi(
                id = it.id ?: "",
                state = it.state ?: "",
                postalCode = it.postalCode ?: 0,
                isPrimary = it.isPrimary ?: false,
                city = it.city ?: ""
            )
        }
    }
}