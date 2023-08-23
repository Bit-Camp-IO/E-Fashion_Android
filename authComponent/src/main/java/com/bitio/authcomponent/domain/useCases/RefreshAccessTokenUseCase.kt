package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository

class RefreshAccessTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            Result.success(repository.refreshAccessToken())
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}