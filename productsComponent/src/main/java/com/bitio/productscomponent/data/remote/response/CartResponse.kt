package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.cart.Cart
import com.bitio.productscomponent.domain.entities.cart.CartItem
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    override val items: List<CartItem>,
    override val subTotal: Double,
    override val tax: Int,
    override val total: Double,
    override val totalQuantity: Double
) : Cart
