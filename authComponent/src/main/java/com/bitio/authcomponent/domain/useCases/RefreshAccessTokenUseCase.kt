package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository

class RefreshAccessTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(){
        repository.refreshAccessToken()
    }
}