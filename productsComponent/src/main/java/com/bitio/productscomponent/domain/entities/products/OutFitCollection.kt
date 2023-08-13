package com.bitio.productscomponent.domain.entities.products

interface OutFitCollection {
    val id: Int
    val name: String
    val image: String
    val description: String
    val productInstances: List<ProductInstance>
}