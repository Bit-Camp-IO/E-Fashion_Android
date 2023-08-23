package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.bitio.productscomponent.domain.entities.products.Product

@Stable
data class UiProduct(
    val id: String,
    val name: String,
    val image: String,
    val price: Float,
    val isFavorite: MutableState<Boolean>
)

fun Product.toUiProduct() = UiProduct(id, title, image, price, mutableStateOf(false))