package com.bitio.notificationscomponent.data.remote.response

import com.bitio.notificationscomponent.domain.model.Notification
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(override val device: String):Notification
