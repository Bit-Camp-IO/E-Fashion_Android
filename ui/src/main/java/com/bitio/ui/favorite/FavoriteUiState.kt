package com.bitio.ui.favorite

import com.bitio.productscomponent.domain.entities.products.Product

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)