package com.bitio.notificationscomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper


interface NotificationRepository {
    suspend fun addDeviceTokenToNotification(deviceToken:String): ResponseWrapper<Nothing>
}