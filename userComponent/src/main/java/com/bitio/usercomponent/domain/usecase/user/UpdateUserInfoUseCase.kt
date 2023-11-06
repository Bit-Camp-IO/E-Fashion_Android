package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.repository.UserRepository

class UpdateUserInfoUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userProfile: UserProfile): ResponseStatus<UserProfileResponse?> {
        return try {
            val response = repository.updateUserInformation(userProfile)
            if (response.data != null) {
                ResponseStatus.Success(response.data)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }
}
