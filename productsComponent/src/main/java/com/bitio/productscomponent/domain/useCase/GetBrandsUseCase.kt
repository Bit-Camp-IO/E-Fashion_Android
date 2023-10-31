package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.model.Brand
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetBrandsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<List<Brand>> {
        return try {
            Result.success(repository.getBrands())

        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}