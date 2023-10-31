package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.products.ColorOfProduct
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorOptionResponse(
    @SerialName("_id")
    override val id: String,
    override val hex: String,
    override val name: String
) : ColorOfProduct