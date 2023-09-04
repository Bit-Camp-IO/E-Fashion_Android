package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.domain.entities.User
import com.bitio.usercomponent.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserInformationUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<User> {
        return repository.getSavedUserInformation()
    }
}
