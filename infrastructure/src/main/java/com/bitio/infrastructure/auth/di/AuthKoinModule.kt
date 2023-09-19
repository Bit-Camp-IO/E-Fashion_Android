package com.bitio.infrastructure.auth.di

import com.bitio.authcomponent.data.local.AuthDao
import com.bitio.authcomponent.data.remote.AuthApi
import com.bitio.authcomponent.data.repository.AuthRepositoryImpl
import com.bitio.authcomponent.domain.repository.AuthRepository
import com.bitio.authcomponent.domain.useCases.auth.GetAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.auth.CheckIfUserLoggedInUseCase
import com.bitio.authcomponent.domain.useCases.auth.LoginUseCase
import com.bitio.authcomponent.domain.useCases.auth.RefreshAccessTokenUseCase
import com.bitio.authcomponent.domain.useCases.auth.RegisterUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateConfirmPasswordUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateEmailUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateFullNameUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidatePasswordUseCase
import com.bitio.authcomponent.domain.useCases.validate.ValidateTermsUseCase
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
    factory { ValidateConfirmPasswordUseCase() }
    factory { ValidateFullNameUseCase() }
    factory { ValidateTermsUseCase() }
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { LoginUseCase(get()) }
    factory { CheckIfUserLoggedInUseCase(get()) }

}