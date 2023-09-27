package com.bitio.authcomponent.domain.useCases.auth

import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.utils.ResponseStatus

class VerifyEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        otp: String,
    ): ResponseStatus<String> {
        return try {
            val response = repository.verifyEmail(otp)
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