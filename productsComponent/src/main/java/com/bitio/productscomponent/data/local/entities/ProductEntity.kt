package com.bitio.productscomponent.data.local.entities

import com.bitio.productscomponent.domain.entities.products.Product

data class ProductEntity(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val price: Float,

): Product
