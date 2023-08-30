package com.bitio.productscomponent.data.local.entities

import com.bitio.productscomponent.domain.entities.products.Product

data class ProductEntity(
    override val id: String,
    override val title: String,
    override val image: String,
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
): Product
