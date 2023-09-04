package com.bitio.usercomponent.domain.usecase

import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.utils.ResponseStatus
import com.bitio.usercomponent.domain.repository.UserRepository


class RefreshUserInfoUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(){
        repository.refreshUserInfo()
    }
}
