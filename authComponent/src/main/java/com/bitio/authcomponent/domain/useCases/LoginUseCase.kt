package com.bitio.authcomponent.domain.useCases

import com.bitio.authcomponent.domain.repository.AuthRepository

class LoginUseCase (private val repository: AuthRepository){
    suspend operator fun invoke(email:String,password:String){
        repository.login(email,password)
    }

}