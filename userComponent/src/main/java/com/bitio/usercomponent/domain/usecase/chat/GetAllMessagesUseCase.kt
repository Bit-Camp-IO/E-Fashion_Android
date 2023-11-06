package com.bitio.usercomponent.domain.usecase.chat

import com.bitio.usercomponent.domain.model.Chat
import com.bitio.usercomponent.domain.repository.UserRepository

class GetAllMessagesUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(chatId: String): Result<List<Chat>> {
        return try {
            Result.success(repository.getAllMessages(chatId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}