package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.domain.repository.ProductRepository

class AddProductToCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(){

    }
}