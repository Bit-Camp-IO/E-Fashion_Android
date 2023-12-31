package com.bitio.infrastructure.user.di

import com.bitio.infrastructure.retrofitConfiguration.AuthInterceptor
import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.infrastructure.user.local.UserDaoAdapter
import com.bitio.infrastructure.user.local.data_store.ProfileSettingsDaoImpl
import com.bitio.infrastructure.user.remote.RealtimeMessagingClientImpl
import com.bitio.infrastructure.user.remote.UserService
import com.bitio.usercomponent.data.local.ProfileSettingsDao
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.data.remote.RealtimeMessagingClient
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.repository.ProfileSettingsRepositoryImpl
import com.bitio.usercomponent.data.repository.UserRepositoryImpl
import com.bitio.usercomponent.domain.repository.ProfileSettingsRepository
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.usecase.chat.AskNewChatUseCase
import com.bitio.usercomponent.domain.usecase.chat.GetAllMessagesUseCase
import com.bitio.usercomponent.domain.usecase.chat.GetStatusChatUseCase
import com.bitio.usercomponent.domain.usecase.chat.SendMessageUseCase
import com.bitio.usercomponent.domain.usecase.profile.GetProfileSettingsUseCase
import com.bitio.usercomponent.domain.usecase.profile.SaveProfileSettingsUseCase
import com.bitio.usercomponent.domain.usecase.user.DeleteUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.AddUserLocationUseCase
import com.bitio.usercomponent.domain.usecase.user.AddUserImageUseCase
import com.bitio.usercomponent.domain.usecase.user.GetSavedUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.user.GetUserInformationUseCase
import com.bitio.usercomponent.domain.usecase.user.RefreshUserInfoUseCase
import com.bitio.usercomponent.domain.usecase.user.UpdateUserInfoUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import org.koin.dsl.module

fun provideHttpClient(): HttpClient {
    return HttpClient(CIO) {
        install(WebSockets)
    }
}

val userKoinModule = module {
    single { provideHttpClient() }
    single<RealtimeMessagingClient> { RealtimeMessagingClientImpl() }

    single { AuthInterceptor() }
    single<UserApi> { UserService(get()).service }
    single { get<AppDatabase>().userRoomDao() }
    single<UserDao> { UserDaoAdapter(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }


    factory { RefreshUserInfoUseCase(get()) }
    factory { GetSavedUserInformationUseCase(get()) }
    factory { GetUserInformationUseCase(get()) }
    factory { UpdateUserInfoUseCase(get()) }
    factory { GetUserLocationUseCase(get()) }
    factory { AddUserImageUseCase(get()) }
    factory { AddUserLocationUseCase(get()) }
    factory { DeleteUserLocationUseCase(get()) }

    single<ProfileSettingsDao> { ProfileSettingsDaoImpl(get()) }
    single<ProfileSettingsRepository> { ProfileSettingsRepositoryImpl(get()) }

    factory { SaveProfileSettingsUseCase(get()) }
    factory { GetProfileSettingsUseCase(get()) }

    factory { GetStatusChatUseCase(get()) }
    factory { AskNewChatUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetAllMessagesUseCase(get()) }

}