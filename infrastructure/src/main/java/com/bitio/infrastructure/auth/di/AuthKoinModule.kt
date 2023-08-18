package com.bitio.infrastructure.auth.di

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.repository.AuthRepositoryImpl
import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.RefreshAccessTokenUseCase
import com.bitio.infrastructure.auth.local.AuthDaoImpl
import com.bitio.infrastructure.auth.remote.AuthRetrofit
import org.koin.dsl.module


val authModule = module {
    single<AuthApi> { AuthRetrofit.service }
    single<AuthDao> { AuthDaoImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { GetAccessTokenUseCase(get()) }
    factory { RefreshAccessTokenUseCase(get()) }

}