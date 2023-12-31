package com.bitio.infrastructure.product.local.productModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bitio.productscomponent.domain.model.categories.Category
import com.bitio.productscomponent.domain.model.categories.GenderType

@Entity(tableName = "Categories")
data class CategoryDto(
    @PrimaryKey override val id: String,
    override val name: String,
    override val image: String,
    override val genderType: GenderType
) : Category

