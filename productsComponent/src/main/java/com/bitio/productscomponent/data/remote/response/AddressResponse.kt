package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.order.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    override val longitude: Double,
    override val latitude: Double
) : Address
