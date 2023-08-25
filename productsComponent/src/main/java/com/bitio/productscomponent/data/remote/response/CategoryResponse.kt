package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    override val id: String,
    override val name: String,
    override val image: String,
    override val gender: GenderType,
) : Category
