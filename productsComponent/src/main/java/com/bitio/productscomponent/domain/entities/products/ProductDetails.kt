package com.bitio.productscomponent.domain.entities.products

import com.bitio.productscomponent.domain.entities.selectable.ColorOption

interface ProductDetails {
    val id: String
    val name: String
    val images: List<String>
    val price: Float
    val oldPrice: Float
    val discount: Float
    val description: String
    val brandName: String
    val isAvailable: Boolean
    val isNew: Boolean
    val rate: Float
    val colors: List<ColorOption>
    val sizes: List<String>
    //  val selectableProperties: List<SelectableProperty>
}