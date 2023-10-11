package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.domain.repository.ProductRepository

class DeleteCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(cardId: String): Result<String?> {
        return try {
            val response = repository.deleteCart(cardId)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(response.error!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
