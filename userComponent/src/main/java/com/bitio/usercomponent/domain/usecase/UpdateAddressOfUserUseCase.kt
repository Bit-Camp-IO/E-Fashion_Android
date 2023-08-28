package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.domain.repository.UserRepository

class UpdateAddressOfUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(addressId: String) {
        repository.updateUserImage(addressId)
    }
}