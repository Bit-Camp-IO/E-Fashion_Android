package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(fullName: String, email: String, password: String) {
        repository.register(fullName, email, password)
    }

}