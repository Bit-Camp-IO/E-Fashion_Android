package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.model.Chat
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    override val content: String,
    override val date: String,
    override val me: Boolean
) : Chat
