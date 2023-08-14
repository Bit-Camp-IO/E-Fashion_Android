package com.bitio.productscomponent.domain.entities.products

import com.bitio.productscomponent.domain.entities.selectable.SelectedProperty

interface ProductInstance {
    val productId: Int
    val name: String
    val image: String
    val description: String
    val selectedProperties: List<SelectedProperty>
}