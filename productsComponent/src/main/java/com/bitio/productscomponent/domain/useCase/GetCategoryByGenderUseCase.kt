package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetCategoryByGenderUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(genderType: GenderType): Result<List<Category>> {
        val data = repository.getCategoriesByGender(genderType)
      return  try {
            Result.success(data)
        } catch (e: Throwable) {

            Result.failure(e)
        }
    }
}