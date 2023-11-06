package com.bitio.usercomponent.domain.usecase.user

import com.bitio.usercomponent.domain.repository.UserRepository


class RefreshUserInfoUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(){
        repository.refreshUserInfo()
    }
}
