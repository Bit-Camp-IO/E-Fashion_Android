package com.bitio.productscomponent.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CartQuantityBody(
    val id:String,
    val quantity:Int
)
