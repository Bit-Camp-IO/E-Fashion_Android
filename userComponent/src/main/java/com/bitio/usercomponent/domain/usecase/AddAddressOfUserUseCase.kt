package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.domain.repository.UserRepository

class AddAddressOfUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(addressId: String) {
        repository.addUserImage(addressId)
    }
}