package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.favorites.Favorite
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProductResponse(
    override val id: String,
    override val title: String,
    override val price: Int,
    override val imageUrl: String
) : Favorite
