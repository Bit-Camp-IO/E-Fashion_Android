package com.bitio.productscomponent.domain.entities.cart

interface CartItem {
    val id: Int
    val size: String
    val color: String
    val quantity: Int
    val price: Int
    val stock: Int
    val title: String
    val imageUrl: String
    val productId: Int
    val totalPrice: Int
    val oldPrice: Int
    val oldTotalPrice: Int
}