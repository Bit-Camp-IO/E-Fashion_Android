package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.cart.CartItem
import kotlinx.serialization.Serializable

@Serializable
data class CartItemResponse(
    override val productId: String,
    override val size: String,
    override val color: String,
    override val quantity: Int,
    override val price: Int,
    override val stock: Int,
    override val title: String,
    override val imageUrl: String,
    override val totalPrice: Int,
    override val oldPrice: Int,
    override val oldTotalPrice: Int
) : CartItem
