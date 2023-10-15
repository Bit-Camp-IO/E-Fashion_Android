package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.domain.repository.ProductRepository

class DeleteProductFromCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(cartId: String): Result<CartResponse?> {
        return try {
            val response = repository.deleteProductFromCart(cartId)
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
