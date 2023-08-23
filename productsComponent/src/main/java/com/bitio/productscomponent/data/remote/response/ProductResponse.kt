package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.products.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    override val id: String,
    override val title: String,
    override val image: String="",
    override val price: Float,
) : Product
