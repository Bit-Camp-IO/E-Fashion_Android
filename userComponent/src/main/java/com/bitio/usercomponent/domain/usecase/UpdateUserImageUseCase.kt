package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.domain.repository.UserRepository

class UpdateUserImageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(image: String) {
        repository.updateUserImage(image)
    }
}