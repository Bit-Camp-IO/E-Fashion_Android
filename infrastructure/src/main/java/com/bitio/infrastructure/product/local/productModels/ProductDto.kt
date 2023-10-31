package com.bitio.infrastructure.product.local.productModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bitio.productscomponent.domain.model.products.Product

@Entity(tableName = "products")
data class ProductDto constructor(
    @PrimaryKey override val id: String,
    override val title: String,
    override val image: String,
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
    val isFavorite: Boolean,
) : Product {
    constructor(product: Product, isFavorite: Boolean) : this(
        product.id,
        product.title,
        product.image,
        product.price,
        product.oldPrice,
        product.discount,
        isFavorite
    )
}
