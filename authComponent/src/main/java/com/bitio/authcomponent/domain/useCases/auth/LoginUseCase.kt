package com.bitio.authcomponent.domain.useCases.auth

import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.utils.ResponseStatus

class LoginUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): ResponseStatus<String> {
        return try {
            val response = repository.login(email, password)
                if (response.data != null) {
                    val refreshToken = response.data?.refreshToken!!
                    repository.updateRefreshToken(refreshToken)
                    ResponseStatus.Success(response.status)
                } else {
                    ResponseStatus.Error(response.message)
                }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }

}