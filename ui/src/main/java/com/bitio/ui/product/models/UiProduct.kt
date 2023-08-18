package com.bitio.ui.product.models

import com.bitio.productscomponent.domain.entities.products.Product
import javax.annotation.concurrent.Immutable

@Immutable
data class UiProduct(
    val id: Int,
    val name: String,
    val image: String,
    val price: Float
)

fun Product.toUiProduct() = UiProduct(id, name, image, price)