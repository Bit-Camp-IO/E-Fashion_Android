package com.bitio.ui.product.favorite

import com.bitio.productscomponent.domain.model.products.ColorOfProduct
import com.bitio.productscomponent.domain.model.products.ProductDetails

data class ProductUiState(
    val isLoading: Boolean = false,
    val product: ProductDetails = Product(),
    val errorMessage: String = "",
)

data class Product(
    override val id: String = "",
    override val name: String = "",
    override val images: List<String> = emptyList(),
    override val price: Float = 0f,
    override val oldPrice: Float = 0f,
    override val discount: Float = 0f,
    override val description: String = "",
    override val brandName: String = "",
    override val isAvailable: Boolean = false,
    override val isNew: Boolean = false,
    override val rate: Float = 0f,
    override val colors: List<ColorOfProduct> = emptyList(),
    override val sizes: List<String> = emptyList(),
    override val stock: Int = 0
) : ProductDetails
