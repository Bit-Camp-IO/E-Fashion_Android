package com.bitio.productscomponent.domain.useCase.favorite

import com.bitio.productscomponent.domain.model.GeneralCart
import com.bitio.productscomponent.domain.repository.ProductRepository

class AddProductToCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(generalCart: GeneralCart): Result<String?> {
        return try {
            val response = repository.addProductToCart(generalCart)
            if (response.data != null) {
                Result.success(response.status)
            } else {
                Result.failure(response.error!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}