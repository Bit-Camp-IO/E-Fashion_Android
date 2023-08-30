package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType

@Stable
data class UiCategory(
    override val id: String,
    override val name: String,
    override val image: String,
    override val genderType: GenderType,
    val selectedState: MutableState<Boolean>,
    val onCategoryClicked: (String) -> Unit
) : Category

fun Category.toUiCategory(isSelected: Boolean, onCategoryClicked: (String) -> Unit): UiCategory {
    return UiCategory(
        id, name,
        image, genderType,
        mutableStateOf(isSelected), onCategoryClicked
    )
}