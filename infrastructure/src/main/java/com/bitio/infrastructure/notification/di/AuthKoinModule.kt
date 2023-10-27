package com.bitio.infrastructure.notification.di

import com.bitio.infrastructure.notification.remote.NotificationRetrofit
import com.bitio.notificationscomponent.data.remote.NotificationApi
import com.bitio.notificationscomponent.data.repository.NotificationRepositoryImpl
import com.bitio.notificationscomponent.domain.repository.NotificationRepository
import com.bitio.notificationscomponent.domain.usecase.AddDeviceTokenToNotificationUseCase
import org.koin.dsl.module

val notificationKoinModule = module {
    single<NotificationApi> { NotificationRetrofit.service }
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    factory { AddDeviceTokenToNotificationUseCase(get()) }
}