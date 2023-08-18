package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository

class GetAccessTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): String {
        return repository.getAccessToken()
    }
}