package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.repository.UserRepository

class GetSavedUserInformationUseCase(private val repository: UserRepository) {
    operator fun invoke() {
    }
}
