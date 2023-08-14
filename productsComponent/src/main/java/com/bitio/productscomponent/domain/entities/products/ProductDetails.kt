package com.bitio.productscomponent.domain.entities.products

import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty

interface ProductDetails {
    val id: Int
    val name: String
    val images: List<String>
    val price: Float
    val description: String
    val brandId: Int
    val brandName: String
    val selectableProperties: List<SelectableProperty>
}