package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.domain.model.ChatStatus
import kotlinx.serialization.Serializable

@Serializable
data class ChatStatusResponse(
    override val id: String,
    override val status: String,
    override val user: String,
) : ChatStatus
