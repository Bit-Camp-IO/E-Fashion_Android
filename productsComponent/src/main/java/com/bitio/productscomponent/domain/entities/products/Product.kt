package com.bitio.productscomponent.domain.entities.products

import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty

interface Product {
    val id: Int
    val name: String
    val image: String
    val price: Float

}

