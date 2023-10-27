package com.bitio.notificationscomponent.data.remote.request

import com.bitio.notificationscomponent.domain.model.Notification
import kotlinx.serialization.Serializable

@Serializable
data class NotificationBody(override val device: String):Notification
