package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.repository.UserRepository

class AddAddressOfUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(address: Address) {
        repository.addAddressOfUser(address)
    }
}