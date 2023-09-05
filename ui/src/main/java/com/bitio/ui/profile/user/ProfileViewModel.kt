package com.bitio.ui.profile.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.AddAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.AddUserImageUseCase
import com.bitio.usercomponent.domain.usecase.GetSavedUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.RefreshUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.UpdateUserInfoUseCase
import kotlinx.coroutines.launch
import java.io.File


class ProfileViewModel(
    private val refreshUserInfoUseCase: RefreshUserInfoUseCase,
    private val getSavedUserInformationUseCase: GetSavedUserInformationUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getAddressesOfUseCase: GetAddressesOfUseCase,
    private val addAddressOfUserUseCase: AddAddressOfUserUseCase,
    private val deleteAddressOfUserUseCase: DeleteAddressOfUserUseCase,
    private val addUserImageUseCase: AddUserImageUseCase,
) : ViewModel() {

    private val _profileUiState = mutableStateOf(ProfileUiState())
    val profileUiState = _profileUiState

    val fullName = mutableStateOf("")

    val email = mutableStateOf("")

    val phoneNumber = mutableStateOf("")

    init {
        refreshUserInfo()
    }

    private fun refreshUserInfo() {
        viewModelScope.launch {
            refreshUserInfoUseCase()
            getUserInfo()
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            getSavedUserInformationUseCase().collect { user ->
                _profileUiState.value = ProfileUiState(
                    loading = false,
                    profileUi = ProfileUi(
                        fullName = user.fullName ?: "",
                        email = user.email ?: "",
                        phoneNumber = user.phoneNumber ?: "",
                        profileImage = user.profileImage
                            ?: "https://th.bing.com/th/id/OIP.SzixlF6Io24jCN67HHZulAHaLH?w=130&h=195&c=7&r=0&o=5&dpr=1.3&pid=1.7",
                        settingsUi = SettingsUi(
                            language = "",
                            addresses = emptyList()
                        )
                    )
                )
            }
        }
    }

    fun updateUserInfo(userUiState: UserUiState) {
        viewModelScope.launch {
            _profileUiState.value = ProfileUiState(loading = true)
            when (val response = updateUserInfoUseCase(userUiState)) {
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

    fun addUserImage(file: File) {
        viewModelScope.launch {
            when (val response = addUserImageUseCase(file)) {
                is ResponseStatus.Success -> {
                    Log.d(this::class.simpleName, "Success addUserImage: ${response.data}")
                }

                is ResponseStatus.Error -> {
                    Log.d(this::class.simpleName, "Error addUserImage: ${response.errorMessage}")
                }
            }
        }
    }
}