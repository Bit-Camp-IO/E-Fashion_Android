package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.data.remote.request.CartItemBody
import com.bitio.productscomponent.domain.model.cart.Cart
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    override val items: List<CartItemBody>,
    override val subtotal: Int,
    override val tax: Int,
    override val total: Int,
    override val totalQuantity: Int
) : Cart
