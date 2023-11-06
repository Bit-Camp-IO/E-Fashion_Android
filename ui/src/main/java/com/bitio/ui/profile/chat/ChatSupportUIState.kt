package com.bitio.ui.profile.chat

import com.bitio.usercomponent.domain.model.Chat

data class ChatSupportUIState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val chats: List<Chat> = emptyList()
)


interface ChatSupport {
    val id: String
    val message: String
    val date: String
}

data class Sender(
    override val id: String,
    override val message: String,
    override val date: String
) : ChatSupport

data class Receiver(
    override val content: String,
    override val date: String,
    override val me: Boolean
) : Chat