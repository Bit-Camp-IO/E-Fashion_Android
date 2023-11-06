package com.bitio.usercomponent.domain.usecase.chat

import com.bitio.usercomponent.domain.model.SenderChat
import com.bitio.usercomponent.domain.repository.UserRepository

class SendMessageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(chatId: String, content: String): Result<SenderChat?> {
        return try {
            val response = repository.sendMessage(chatId, content)
            if (response.status == "success") {
                println("chatId = $chatId   content = $content")
                Result.success(response.data)
            } else {
                println("chatId = ${response.status}")
                Result.failure(response.error!!)
            }
        } catch (e: Exception) {
            println("chatId = ${e.message}")
            Result.failure(e)
        }
    }
}