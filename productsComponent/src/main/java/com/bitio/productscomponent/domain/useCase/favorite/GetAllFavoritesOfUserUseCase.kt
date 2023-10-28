package com.bitio.productscomponent.domain.useCase.favorite

import com.bitio.productscomponent.data.remote.response.FavoriteProductResponse
import com.bitio.productscomponent.domain.entities.favorites.Favorite
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.sharedcomponent.data.ResponseWrapper

class GetAllFavoritesOfUserUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<ResponseWrapper<List<Favorite>>> {
        return try {
            val data = repository.getFavoritesOfUser()
            Result.success(data)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}