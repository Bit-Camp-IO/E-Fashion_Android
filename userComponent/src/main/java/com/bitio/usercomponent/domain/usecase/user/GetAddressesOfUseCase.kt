package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.repository.UserRepository

class GetAddressesOfUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(): ResponseStatus<List<Address>?> {
        return try {
            val response = repository.getAddressesOfUser()
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