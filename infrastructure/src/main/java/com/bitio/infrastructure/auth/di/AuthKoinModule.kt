package com.bitio.infrastructure.auth.di

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.repository.AuthRepositoryImpl
import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.GetUserLoginState
import com.bitio.authcomponent.domain.useCases.LoginUseCase
import com.bitio.authcomponent.domain.useCases.RefreshAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.RegisterUseCase
import com.bitio.infrastructure.auth.local.AuthDaoImpl
import com.bitio.infrastructure.auth.remote.AuthRetrofit
import org.koin.dsl.module


val authKoinModule = module {
    single<AuthApi> { AuthRetrofit.service }
    single<AuthDao> { AuthDaoImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factory { GetAccessTokenUseCase(get()) }
    factory { RefreshAccessTokenUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetUserLoginState(get()) }

}