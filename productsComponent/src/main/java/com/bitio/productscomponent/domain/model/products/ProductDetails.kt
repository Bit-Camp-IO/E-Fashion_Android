package com.bitio.productscomponent.domain.model.products

interface ProductDetails {
    val id: String
    val name: String
    val images: List<String>
    val price: Float
    val oldPrice: Float
    val discount: Float
    val description: String
    val brandName: String
    val isAvailable: Boolean
    val isNew: Boolean
    val rate: Float
    val colors: List<ColorOfProduct>
    val sizes: List<String>
    val stock: Int
    //  val selectableProperties: List<SelectableProperty>
}