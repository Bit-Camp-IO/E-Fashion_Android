package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.order.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    override val id: String,
    override val address: AddressResponse,
    override val paymentMethod: String,
    override val price: Int,
    override val tax: Int,
    override val totalPrice: Int,
    override val totalQuantity: Int,
    override val status: Int
) : Order
