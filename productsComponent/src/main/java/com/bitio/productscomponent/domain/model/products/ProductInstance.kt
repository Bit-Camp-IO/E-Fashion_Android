package com.bitio.productscomponent.domain.model.products

import com.bitio.productscomponent.domain.model.selectable.SelectedProperty

interface ProductInstance {
    val productId: Int
    val name: String
    val image: String
    val description: String
    val selectedProperties: List<SelectedProperty>
}