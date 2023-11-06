package com.bitio.infrastructure.notification.remote

import com.bitio.notificationscomponent.data.remote.NotificationApi
import com.bitio.notificationscomponent.data.remote.request.NotificationBody
import com.bitio.notificationscomponent.domain.model.Notification
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApiRetrofit : NotificationApi {

    @POST("/api/notif/subscribe")
    override suspend fun addDeviceTokenToNotification(@Body notificationBody: NotificationBody): ResponseWrapper<Nothing>
}