package com.bitio.usercomponent.domain.usecase.chat


import com.bitio.usercomponent.domain.model.ChatStatus
import com.bitio.usercomponent.domain.repository.UserRepository

class AskNewChatUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(chatStatus: ChatStatus){

    }
}