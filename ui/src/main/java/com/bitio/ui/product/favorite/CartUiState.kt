package com.bitio.ui.product.favorite

import com.bitio.productscomponent.domain.model.GeneralCart


data class CartUiState(
    val isLoading: Boolean = false,
    val message: String = "",
)

data class CartItemUiState(
    override val id: String,
    override val size: String,
    override val color: String,
    override val quantity: Int
) : GeneralCart
