package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.entities.User
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.repository.UserRepository

class UpdateUserInfoUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): ResponseStatus<ProfileResponse?> {
        return try {
            val response = repository.updateUserInformation(user)
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
