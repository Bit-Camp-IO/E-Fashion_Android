package com.bitio.ui.profile.chat

data class ChatSupportUIState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val chatSupport: List<ChatSupport> = emptyList()
)


interface ChatSupport {
    val id: String
    val message: String
    val messageTime: String
}

data class Sender(
    override val id: String,
    override val message: String,
    override val messageTime: String
) : ChatSupport

data class Receiver(
    override val id: String,
    override val message: String,
    override val messageTime: String
) : ChatSupport