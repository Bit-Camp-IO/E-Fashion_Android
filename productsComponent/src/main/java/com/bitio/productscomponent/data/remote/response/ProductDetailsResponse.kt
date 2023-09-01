package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.products.ProductDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsResponse(
    override val id: String,
    @SerialName(value = "title") override val name: String,
    @SerialName(value = "imagesUrl") override val images: List<String>,
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
    override val description: String,
    @SerialName(value = "brand") override val brandName: String,
    @SerialName(value = "available") override val isAvailable: Boolean,
    override val isNew: Boolean,
    override val rate: Float,
    override val colors: List<ColorOptionResponse>,
    override val sizes: List<String>
) : ProductDetails
