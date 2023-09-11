package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetAccessTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): String {

        try {
            return repository.getAccessToken()
        } catch (e: Throwable) {
            return "abc"
        }

    }

    fun getAsStream(): Flow<String> {
        return repository.getAccessTokenStream()
    }

    fun getQuickAccessToken() = repository.quickRetrieveAccessToken() ?: ""
}