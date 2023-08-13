package com.bitio.productscomponent.domain.entities.products

interface ProductWithOffer : Product {
    val oldPrice: Float
}