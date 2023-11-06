package com.bitio.ui.profile.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.usercomponent.domain.usecase.user.AddUserImageUseCase
import com.bitio.usercomponent.domain.usecase.user.DeleteUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetSavedUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.user.RefreshUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.user.UpdateUserInfoUseCase
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.utils.TAG_APP
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.io.File

@KoinViewModel
class UserViewModel(
    private val refreshUserInfoUseCase: RefreshUserInfoUseCase,
    private val getSavedUserInformationUseCase: GetSavedUserInformationUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val deleteUserLocationUseCase: DeleteUserLocationUseCase,
    private val addUserImageUseCase: AddUserImageUseCase,
) : ViewModel() {

    private val _userProfileUiState = mutableStateOf(UserProfileUiState())
    val userProfileUiState = _userProfileUiState

    val fullName = mutableStateOf("")
    val email = mutableStateOf("")
    val phoneNumber = mutableStateOf("")

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            val response = getUserInformationUseCase()
            response.onSuccess { profile ->
                profile?.let { user ->
                    initialFiled(user)
                    _userProfileUiState.value = UserProfileUiState(
                        profileUi = ProfileUi(
                            fullName = user.fullName,
                            phoneNumber = user.phoneNumber,
                            email = user.email,
                            id = user.id,
                            address = user.address,
                            isVerified = user.isVerified,
                            settings = user.settings,
                            provider = user.provider,
                            profileImage = user.profileImage,
                        )
                    )
                }
            }
            response.onFailure {
                Log.d(TAG_APP, "getUserInformation: ${it.message}")
            }
        }
    }

    private fun initialFiled(userProfile: UserProfile) {
        email.value = userProfile.email
        phoneNumber.value = userProfile.phoneNumber
        fullName.value = userProfile.fullName
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