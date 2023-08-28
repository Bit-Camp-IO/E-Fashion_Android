package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.data.remote.response.UserResponse
import com.bitio.usercomponent.domain.ResponseStatus
import com.bitio.usercomponent.domain.repository.UserRepository

class GetUserInfoUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): ResponseStatus<UserResponse?> {
        return try {
            val response = repository.getUserInformation()
            if (response.status == "200") {
                ResponseStatus.Success(response.data)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }
}
