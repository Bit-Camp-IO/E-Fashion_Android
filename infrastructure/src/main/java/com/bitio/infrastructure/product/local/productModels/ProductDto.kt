package com.bitio.infrastructure.product.local.productModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bitio.productscomponent.domain.entities.products.Product

@Entity(tableName = "products")
data class ProductDto constructor(
    @PrimaryKey override val id: String,
    override val title: String,
    override val image: String,
    override val price: Float,
    val isFavorite: Boolean
) : Product {
    constructor(product: Product, isFavorite: Boolean) : this(
        product.id,
        product.title,
        product.image,
        product.price,
        isFavorite
    )
}