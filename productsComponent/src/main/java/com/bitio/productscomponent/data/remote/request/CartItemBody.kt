package com.bitio.productscomponent.data.remote.request

import com.bitio.productscomponent.domain.model.GeneralCart
import kotlinx.serialization.Serializable

@Serializable
data class CartItemBody(
    override val id: String,
    override val size: String,
    override val color: String,
    override val quantity: Int
) : GeneralCart
