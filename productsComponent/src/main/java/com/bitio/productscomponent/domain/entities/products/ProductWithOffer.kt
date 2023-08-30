package com.bitio.productscomponent.domain.entities.products

interface ProductWithOffer : Product {
    override val oldPrice: Float
}