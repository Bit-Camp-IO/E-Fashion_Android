package com.bitio.authcomponent.domain.useCases.auth

import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.utils.ResponseStatus

class ForgotPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
    ): ResponseStatus<String> {
        return try {
            val response = repository.forgotPassword(email)
            if (response.status == "success") {
                ResponseStatus.Success(response.status)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }
}