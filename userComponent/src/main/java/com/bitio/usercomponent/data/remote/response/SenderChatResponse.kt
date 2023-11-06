package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.model.SenderChat
import kotlinx.serialization.Serializable

@Serializable
data class SenderChatResponse(
    override val content: String,
    override val date: String,
    override val sender: String,
    override val me: Boolean
) : SenderChat
