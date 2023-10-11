package com.bitio.productscomponent.domain.entities.cart

interface Cart {
    val items: List<CartItem>
    val subTotal:Double
    val tax:Int
    val total:Double
    val totalQuantity:Double
}