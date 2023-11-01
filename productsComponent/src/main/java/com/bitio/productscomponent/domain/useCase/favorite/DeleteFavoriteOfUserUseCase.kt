package com.bitio.productscomponent.domain.useCase.favorite

import com.bitio.productscomponent.domain.repository.ProductRepository

class DeleteFavoriteOfUserUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.removeProductFromFavorite(productId)
    }
}