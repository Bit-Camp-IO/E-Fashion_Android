package com.bitio.productscomponent.domain.entities.cart

interface Cart {
    val items: List<CartItem>
    val subtotal: Int
    val tax: Int
    val total: Int
    val totalQuantity: Int
}