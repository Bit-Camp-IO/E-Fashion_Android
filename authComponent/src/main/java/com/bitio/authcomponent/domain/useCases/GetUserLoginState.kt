package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow

class GetUserLoginState(private val repository: AuthRepository) {
    operator fun invoke(): StateFlow<Boolean> {
        return  repository.isUserLoggedIn()
    }
}