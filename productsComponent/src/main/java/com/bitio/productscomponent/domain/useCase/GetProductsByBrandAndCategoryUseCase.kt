package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.repository.ProductRepository


class GetProductsByBrandAndCategoryUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(
        brand: String? = null,
        categoriesIds: List<String> = listOf(),
        hasDiscount: Boolean = false,
        page: Int = 1,
        limit: Int = 20
    ): Result<List<Product>> {
        return try {
            val data = repository.getProductsByCategoryAndBrand(
                brand,
                categoriesIds,
                hasDiscount,
                page,
                limit
            )
            Result.success(data)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}