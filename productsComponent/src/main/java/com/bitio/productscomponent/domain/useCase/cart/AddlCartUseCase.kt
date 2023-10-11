package com.bitio.productscomponent.domain.useCase.cart

import com.bitio.productscomponent.domain.repository.ProductRepository

class AddlCartUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(){

    }
}