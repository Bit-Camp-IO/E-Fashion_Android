package com.bitio.infrastructure.user.di

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.repository.AuthRepositoryImpl
import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.LoginUseCase
import com.bitio.authcomponent.domain.useCases.RefreshAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.RegisterUseCase
import com.bitio.infrastructure.auth.local.AuthDaoImpl
import com.bitio.infrastructure.user.remote.UserService
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.repository.UserRepositoryImpl
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.GetUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.UpdateAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.UpdateUserImageUseCase
import org.koin.dsl.module

val userKoinModule = module {
    single<UserApi> { UserService.service }
    single<UserRepository> { UserRepositoryImpl(get()) }

    factory { GetUserInfoUseCase(get()) }
    factory { GetAddressesOfUseCase(get()) }
    factory { UpdateUserImageUseCase(get()) }
    factory { UpdateAddressOfUserUseCase(get()) }
    factory { DeleteAddressOfUserUseCase(get()) }

}