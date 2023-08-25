package com.bitio.infrastructure.product.remote.data

import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.domain.entities.categories.GenderType
import kotlinx.serialization.Serializable

@Serializable
data class CategoryAdaptee(
    val id: String,
    val name: String,
    val imageURL: String,
    val gender: Int,
) {
    fun toCategoryResponse() = CategoryResponse(id, name, imageURL, GenderType.getFromId(gender))
}