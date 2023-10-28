package com.bitio.ui.product.favorite

import com.bitio.productscomponent.domain.entities.favorites.Favorite

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val products: List<Favorite> = emptyList(),
    val errorMessage: String = "",
)