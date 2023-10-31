package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.model.categories.Category
import com.bitio.productscomponent.domain.model.categories.GenderType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    override val id: String,
    override val name: String,
    @SerialName("imageURL") override val image: String,
    @SerialName("gender") val genderId: Int

) : Category {
    override val genderType: GenderType = GenderType.getFromId(genderId)

}
