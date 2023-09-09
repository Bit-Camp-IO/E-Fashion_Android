package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.utils.ResponseStatus
import java.io.File


class AddUserImageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(file: File): ResponseStatus<String?> {
        return try {
            val response = repository.addUserImage(file)
            if (response.data != null) {
                ResponseStatus.Success(response.data)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }
}