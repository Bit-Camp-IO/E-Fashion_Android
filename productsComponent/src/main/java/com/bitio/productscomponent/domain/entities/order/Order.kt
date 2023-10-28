package com.bitio.productscomponent.domain.entities.order

interface Order {
    val id: String
    val address: Address
    val paymentMethod: String
    val price: Int
    val tax: Int
    val totalPrice: Int
    val totalQuantity: Int
    val status: Int
}