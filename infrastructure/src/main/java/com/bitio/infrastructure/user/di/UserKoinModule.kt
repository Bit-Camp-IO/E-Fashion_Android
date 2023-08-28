package com.bitio.infrastructure.user.di

import com.bitio.infrastructure.AuthInterceptor
import com.bitio.infrastructure.user.remote.UserService
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.repository.UserRepositoryImpl
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.GetUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.AddAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.AddUserImageUseCase
import org.koin.dsl.module

val userKoinModule = module {
    single { AuthInterceptor() }
    single<UserApi> { UserService(get()).service }
    single<UserRepository> { UserRepositoryImpl(get()) }

    factory { GetUserInfoUseCase(get()) }
    factory { GetAddressesOfUseCase(get()) }
    factory { AddUserImageUseCase(get()) }
    factory { AddAddressOfUserUseCase(get()) }
    factory { DeleteAddressOfUserUseCase(get()) }

}