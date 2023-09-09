package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.repository.UserRepository

class DeleteAddressOfUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(addressId: String) {
        repository.deleteAddressOfUser(addressId)
    }
}