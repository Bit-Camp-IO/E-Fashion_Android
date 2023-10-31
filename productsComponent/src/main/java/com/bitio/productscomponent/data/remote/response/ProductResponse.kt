package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.products.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    override val id: String,
    override val title: String,
    @SerialName(value = "imageUrl")
    override val image: String = "",
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
) : Product
