package com.bitio.ui.product.cart

import com.bitio.productscomponent.domain.entities.cart.Cart
import com.bitio.productscomponent.domain.entities.cart.CartItem

data class CartItemUiState(
    val loading: Boolean = false,
    val message: String = "",
    override val items: List<CartItem> = emptyList(),
    override val subtotal: Int = 0,
    override val tax: Int = 0,
    override val total: Int = 0,
    override val totalQuantity: Int = 0,
) : Cart

data class CartUiState(
    override val productId: String = "",
    override val size: String = "",
    override val color: String = "",
    override val quantity: Int = 0,
    override val price: Int = 0,
    override val stock: Int = 0,
    override val title: String = "",
    override val imageUrl: String = "",
    override val totalPrice: Int = 0,
    override val oldPrice: Int = 0,
    override val oldTotalPrice: Int = 0
) : CartItem