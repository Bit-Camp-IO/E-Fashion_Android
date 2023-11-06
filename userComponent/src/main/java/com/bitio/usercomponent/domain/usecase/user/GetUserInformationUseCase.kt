package com.bitio.usercomponent.domain.usecase.user

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.usercomponent.domain.repository.UserRepository

class GetUserInformationUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<UserProfile?> {
        return try {
            val response = repository.getUserInformation()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}