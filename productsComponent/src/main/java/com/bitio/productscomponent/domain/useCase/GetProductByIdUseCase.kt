package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetProductByIdUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: String): Result<ProductDetails> {
        return try {
            val data = repository.getProductsById(id)
            Result.success(data)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}