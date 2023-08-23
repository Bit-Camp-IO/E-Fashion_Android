package com.bitio.productscomponent.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductsPage(
    val products: List<ProductResponse>,
    val count: Int,
    val page: Int,
    val totalItems: Int,
    val totalPages: Int
)
