package com.bitio.notificationscomponent.data.repository

import com.bitio.notificationscomponent.data.remote.NotificationApi
import com.bitio.notificationscomponent.data.remote.request.NotificationBody
import com.bitio.notificationscomponent.domain.repository.NotificationRepository
import com.bitio.sharedcomponent.data.ResponseWrapper

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi
):NotificationRepository {
    override suspend fun addDeviceTokenToNotification(deviceToken:String): ResponseWrapper<Any> {
        val notificationBody = NotificationBody(deviceToken)
        return notificationApi.addDeviceTokenToNotification(notificationBody)
    }
}