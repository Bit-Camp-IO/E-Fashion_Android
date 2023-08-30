package com.bitio.productscomponent.domain.useCase

import com.bitio.productscomponent.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetFavoriteIdsUseCase(private val repository: ProductRepository) {
     operator fun invoke():StateFlow<HashSet<String>>{
       return repository.getFavoriteIdsFlow()
    }
}