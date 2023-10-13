package com.bitio.productscomponent.domain.entities.cart

interface CartItem {
    val productId: String
    val title: String
    val color: String
    val size: String
    val imageUrl: String
    val stock: Int
    val quantity: Int
    val price: Int
    val totalPrice: Int
    val oldPrice: Int
    val oldTotalPrice: Int
}