package com.bitio.productscomponent.data.remote.responses

import com.bitio.productscomponent.domain.entities.products.Product
data class ProductResponse(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val price: Float,
) : Product
