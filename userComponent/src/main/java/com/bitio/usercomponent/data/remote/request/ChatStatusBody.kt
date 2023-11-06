package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.model.ChatStatus
import kotlinx.serialization.Serializable

@Serializable
data class ChatStatusBody(
    override val id: String,
    override val user: String,
    override val status: String
) : ChatStatus
