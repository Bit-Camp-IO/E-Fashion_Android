package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.model.Location
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.utils.ResponseStatus

class AddUserLocationUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(location: Location): ResponseStatus<Address?> {
        return try {
            val response = repository.addUserLocation(location)
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