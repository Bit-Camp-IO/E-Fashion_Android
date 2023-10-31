package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.model.products.Product
import com.bitio.productscomponent.domain.repository.ProductRepository

class AddOrRemoveProductFromFavorite(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite)
            repository.removeProductFromFavorite(product.id)
        else repository.addProductToFavorites(product)

    }
}