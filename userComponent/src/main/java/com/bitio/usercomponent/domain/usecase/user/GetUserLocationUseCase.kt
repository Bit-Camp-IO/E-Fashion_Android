package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.model.profile.Address
import com.bitio.usercomponent.domain.repository.UserRepository

class GetUserLocationUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(): ResponseStatus<Address?> {
        return try {
            val response = repository.getAddressesOfUser()
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