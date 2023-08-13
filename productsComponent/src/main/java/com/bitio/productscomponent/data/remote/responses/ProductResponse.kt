package com.bitio.productscomponent.data.remote.responses

import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty

data class ProductResponse(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val price: Float,
    override val description: String,
    override val brandId: Int,
    override val brandName: Int,
    override val selectableProperties: List<SelectableProperty<String>>
) : Product
