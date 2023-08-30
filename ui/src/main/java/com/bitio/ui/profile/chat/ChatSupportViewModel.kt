package com.bitio.ui.profile.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ChatSupportViewModel : ViewModel() {

    private val _chatSupportUiState = MutableStateFlow(ChatSupportUIState())
    val chatSupportUiState = _chatSupportUiState.asStateFlow()

    private val chats = mutableListOf<ChatSupport>(
        Sender(
            id = Random.nextInt().toString(),
            message = "Hi There",
            messageTime = "6:06 PM"
        ),
        Receiver(
            id = Random.nextInt().toString(),
            message = "Hi, How can I help you",
            messageTime = "6:07 PM"
        ),
        Sender(
            id = Random.nextInt().toString(),
            message = "I have encountered some issues with payment",
            messageTime = "6:08 PM"
        ),
        Receiver(
            id = Random.nextInt().toString(),
            message = "Could you please provide me with more details about your issues",
            messageTime = "6:09 PM"
        )
    )

    init {
        getAllMessages()
    }

    fun sendMessage(sender: Sender) {
        chats.add(sender)
        getAllMessages()
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            _chatSupportUiState.update {
                it.copy(
                    isLoading = false,
                    chatSupport = chats.reversed()
                )
            }
        }
    }

}