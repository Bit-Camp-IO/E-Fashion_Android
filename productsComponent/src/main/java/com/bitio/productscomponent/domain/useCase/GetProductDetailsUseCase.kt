package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.model.products.ProductDetails
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetProductDetailsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: String): Result<ProductDetails> {
        return try {
            val response = repository.getProductsById(id)
            Result.success(response)
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}