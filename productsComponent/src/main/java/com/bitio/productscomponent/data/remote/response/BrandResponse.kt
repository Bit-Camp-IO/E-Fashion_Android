package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.Brand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrandResponse(
    override val id: String,
    override val name: String,
    override val description: String,
    @SerialName(value = "logo") override val image: String
) : Brand
