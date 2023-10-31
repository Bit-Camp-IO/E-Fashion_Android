package com.bitio.productscomponent.domain.model.products

interface Product {
    val id: String
    val title: String
    val image: String
    val price: Float
    val oldPrice:Float
    val discount:Float
}