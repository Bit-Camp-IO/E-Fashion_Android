package com.bitio.productscomponent.data.remote.request

import com.bitio.productscomponent.domain.entities.cart.CartItem
import kotlinx.serialization.Serializable

@Serializable
data class CartItemBody(
    override val id: Int,
    override val size: String,
    override val color: String,
    override val quantity: Int,
    override val price: Int,
    override val stock: Int,
    override val title: String,
    override val imageUrl: String,
    override val productId: Int,
    override val totalPrice: Int,
    override val oldPrice: Int,
    override val oldTotalPrice: Int
) : CartItem
