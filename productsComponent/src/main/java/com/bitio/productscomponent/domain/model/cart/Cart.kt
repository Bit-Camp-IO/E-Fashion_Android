package com.bitio.productscomponent.domain.model.cart

interface Cart {
    val items: List<CartItem>
    val subtotal: Int
    val tax: Int
    val total: Int
    val totalQuantity: Int
}