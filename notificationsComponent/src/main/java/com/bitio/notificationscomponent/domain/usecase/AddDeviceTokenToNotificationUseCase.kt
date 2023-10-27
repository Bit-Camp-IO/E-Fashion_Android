package com.bitio.notificationscomponent.domain.usecase

import com.bitio.notificationscomponent.domain.repository.NotificationRepository
import com.bitio.sharedcomponent.data.ResponseWrapper

class AddDeviceTokenToNotificationUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(deviceToken:String): Result<ResponseWrapper<Any>> {
        return try {
            val data = repository.addDeviceTokenToNotification(deviceToken)
            Result.success(data)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}