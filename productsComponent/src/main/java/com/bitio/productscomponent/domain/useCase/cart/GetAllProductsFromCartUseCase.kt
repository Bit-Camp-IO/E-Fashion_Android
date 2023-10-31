package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.domain.model.cart.Cart
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetAllProductsFromCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<Cart?> {
        return try {
            val response = repository.getAllProductsFromCart()
            if (response.data != null){
              Result.success(response.data)
            }else{
                Result.failure(response.error!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}