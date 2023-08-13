package com.bitio.productscomponent.domain.entities.products

interface CollectionGroup {
    val id: Int
    val name: String
    val image: String
    val description: String
    val saleRatio: Float
}