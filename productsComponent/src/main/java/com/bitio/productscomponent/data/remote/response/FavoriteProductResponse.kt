package com.bitio.productscomponent.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProductResponse(
    val title: String,
    val price: Float,
    val id: String
)
