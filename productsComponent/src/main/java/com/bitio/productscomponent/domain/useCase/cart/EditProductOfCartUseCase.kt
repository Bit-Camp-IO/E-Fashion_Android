package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditProductOfCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(cartId: String,quantity: Int): Flow<Result<CartResponse?>> {
        return flow {
            try {
                val response = repository.editProductOfCart(cartId,quantity)
                if (response.data != null) {
                    emit(Result.success(response.data))
                } else {
                    emit(Result.failure(response.error!!))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}
