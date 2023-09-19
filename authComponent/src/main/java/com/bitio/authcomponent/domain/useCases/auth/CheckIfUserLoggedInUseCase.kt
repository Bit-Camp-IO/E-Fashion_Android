package com.bitio.authcomponent.domain.useCases.auth

import com.bitio.authcomponent.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckIfUserLoggedInUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.checkIfUserLoggedIn().map { it?.isNotBlank() == true }
    }
}