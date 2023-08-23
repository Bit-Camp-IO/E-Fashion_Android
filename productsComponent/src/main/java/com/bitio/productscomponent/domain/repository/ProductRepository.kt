package com.bitio.productscomponent.domain.repository

import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.products.ProductDetails


interface ProductRepository {
   suspend fun getProductsByCategoryAndBrand(
        brand: String?, categoriesIds: List<String>?, page: Int,
        limit: Int
    ): List<Product>

   suspend fun getProductsById(id: String): ProductDetails


}