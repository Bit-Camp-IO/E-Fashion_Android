package com.bitio.infrastructure.notification.remote

import com.bitio.notificationscomponent.data.remote.NotificationApi
import com.bitio.notificationscomponent.data.remote.request.NotificationBody
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApiRetrofit : NotificationApi {

    @POST("notif/subscribe")
    override suspend fun addDeviceTokenToNotification(@Body notificationBody: NotificationBody): ResponseWrapper<Any>
}