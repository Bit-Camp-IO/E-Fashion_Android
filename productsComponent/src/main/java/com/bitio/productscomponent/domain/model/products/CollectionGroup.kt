package com.bitio.productscomponent.domain.model.products

interface CollectionGroup {
    val id: Int
    val name: String
    val image: String
    val description: String
    val saleRatio: Float
}