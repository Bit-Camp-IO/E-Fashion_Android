package com.bitio.productscomponent.domain.useCase.favorite

import com.bitio.productscomponent.domain.model.favorites.Favorite
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllFavoritesOfUserUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Result<ResponseWrapper<List<Favorite>>>> {
        return flow {
            try {
                emit(Result.success(repository.getFavoritesOfUser()))
            } catch (e: Throwable) {
                emit(Result.failure(e))
            }
        }
    }
}