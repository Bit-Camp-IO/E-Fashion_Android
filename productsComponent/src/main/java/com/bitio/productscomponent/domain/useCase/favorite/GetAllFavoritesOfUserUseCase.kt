package com.bitio.productscomponent.domain.useCase.favorite

import com.bitio.productscomponent.domain.model.favorites.Favorite
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.sharedcomponent.data.ResponseWrapper

class GetAllFavoritesOfUserUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<ResponseWrapper<List<Favorite>>> {
        return try {
            Result.success(repository.getFavoritesOfUser())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}