package com.bitio.productscomponent.domain.entities

import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty

interface Product {
    val id: Int
    val name: String
    val image: String
    val price: Float
    val description: String
    val brandId: Int
    val brandName: Int
    val selectableProperties: List<SelectableProperty<String>>
}

