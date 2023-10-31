package com.bitio.productscomponent.domain.model.products

interface ProductWithOffer : Product {
    override val oldPrice: Float
}