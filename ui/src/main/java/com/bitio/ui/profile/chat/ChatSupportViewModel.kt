package com.bitio.ui.profile.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.usercomponent.domain.usecase.chat.AskNewChatUseCase
import com.bitio.usercomponent.domain.usecase.chat.GetAllMessagesUseCase
import com.bitio.usercomponent.domain.usecase.chat.GetStatusChatUseCase
import com.bitio.usercomponent.domain.usecase.chat.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatSupportViewModel(
    private val askNewChatUseCase: AskNewChatUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getStatusChatUseCase: GetStatusChatUseCase
) : ViewModel() {

    private val _chatSupportUiState = MutableStateFlow(ChatSupportUIState())
    val chatSupportUiState = _chatSupportUiState.asStateFlow()
    private lateinit var _chatId: String

    val content = mutableStateOf("")

    init {
        getStatusChat()
    }

    private fun getStatusChat() {
        viewModelScope.launch {
            getStatusChatUseCase()?.let { chatId ->
                _chatId = chatId
                getAllMessages()
            }
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            _chatSupportUiState.value = ChatSupportUIState(isLoading = true)
            val result = getAllMessagesUseCase(_chatId)
            result.onSuccess {
                _chatSupportUiState.value = ChatSupportUIState(
                    isLoading = false,
                    chats = it
                )
            }
            result.onFailure {
                _chatSupportUiState.value = ChatSupportUIState(
                    isLoading = false,
                    errorMessage = it.message ?: ""
                )
            }
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            getAllMessages()
            sendMessageUseCase(_chatId, content.value)
        }
    }

//    private val chats = mutableListOf<ChatSupport>(
//        Sender(
//            id = Random.nextInt().toString(),
//            message = "Hi There",
//            date = "6:06 PM"
//        ),
//        Receiver(
//            id = Random.nextInt().toString(),
//            message = "Hi, How can I help you",
//            date = "6:07 PM"
//        ),
//        Sender(
//            id = Random.nextInt().toString(),
//            message = "I have encountered some issues with payment",
//            date = "6:08 PM"
//        ),
//        Receiver(
//            id = Random.nextInt().toString(),
//            message = "Could you please provide me with more details about your issues",
//            date = "6:09 PM"
//        )
//    )

//    init {
//        getAllMessages()
//    }

//    fun sendMessage(sender: Sender) {
//        chats.add(sender)
//        getAllMessages()
//    }
//
//    private fun getAllMessages() {
//        viewModelScope.launch {
//            _chatSupportUiState.update {
//                it.copy(
//                    isLoading = false,
//                    receiverChats = chats.reversed()
//                )
//            }
//        }
//    }

}