package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.model.User
import com.bitio.usercomponent.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserInformationUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<User> {
        return repository.getSavedUserInformation()
    }
}
