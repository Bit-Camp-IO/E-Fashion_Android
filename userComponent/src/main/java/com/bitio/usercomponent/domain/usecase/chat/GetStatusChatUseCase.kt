package com.bitio.usercomponent.domain.usecase.chat


import com.bitio.usercomponent.data.remote.response.ChatResponse
import com.bitio.usercomponent.domain.model.Chat
import com.bitio.usercomponent.domain.repository.UserRepository

class GetStatusChatUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): String? {
        return repository.getStatusChat()
    }
}