package com.bitio.ui.profile.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatSupportViewModel : ViewModel() {

    private val _chatSupportUiState = MutableStateFlow(ChatSupportUIState())
    val chatSupportUiState = _chatSupportUiState.asStateFlow()

    init {
        getAllChatSupport()
    }

    private fun getAllChatSupport() {
        viewModelScope.launch {
            delay(1000L)
            _chatSupportUiState.update {
                it.copy(
                    isLoading = false,

                    )
            }
        }
    }
}