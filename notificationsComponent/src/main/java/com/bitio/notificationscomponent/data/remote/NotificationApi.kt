package com.bitio.notificationscomponent.data.remote

import com.bitio.notificationscomponent.data.remote.request.NotificationBody
import com.bitio.sharedcomponent.data.ResponseWrapper

interface NotificationApi {
    suspend fun addDeviceTokenToNotification(notificationBody: NotificationBody):ResponseWrapper<Any>
}