package com.bitio.infrastructure.user.di

import com.bitio.infrastructure.AuthInterceptor
import com.bitio.infrastructure.product.local.room.ProductRoomDao
import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.infrastructure.user.local.UserDaoAdapter
import com.bitio.infrastructure.user.local.UserRoomDao
import com.bitio.infrastructure.user.remote.UserService
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.repository.UserRepositoryImpl
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.usecase.DeleteAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.GetAddressesOfUseCase
import com.bitio.usercomponent.domain.usecase.AddAddressOfUserUseCase
import com.bitio.usercomponent.domain.usecase.AddUserImageUseCase
import com.bitio.usercomponent.domain.usecase.GetSavedUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.RefreshUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.UpdateUserInfoUseCase
import org.koin.dsl.module

val userKoinModule = module {
    single { AuthInterceptor() }
    single<UserApi> { UserService(get()).service }
    single { get<AppDatabase>().userRoomDao() }
    single<UserDao> { UserDaoAdapter(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    factory { RefreshUserInfoUseCase(get()) }
    factory { GetSavedUserInformationUseCase(get()) }
    factory { UpdateUserInfoUseCase(get()) }
    factory { GetAddressesOfUseCase(get()) }
    factory { AddUserImageUseCase(get()) }
    factory { AddAddressOfUserUseCase(get()) }
    factory { DeleteAddressOfUserUseCase(get()) }

}