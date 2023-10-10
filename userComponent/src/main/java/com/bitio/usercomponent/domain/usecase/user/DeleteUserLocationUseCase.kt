package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.utils.ResponseStatus

class DeleteUserLocationUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(addressId: String): ResponseStatus<String> {
        return try {
            val response = repository.deleteUserLocation(addressId)
            if (response.message == "OK") {
                ResponseStatus.Success(response.message)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }
}