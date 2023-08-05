package com.bitio.productscomponent.data.local.dataSource

import com.bitio.productscomponent.domain.entities.Product
import com.bitio.productscomponent.domain.entities.categories.CategoryFilter
import com.bitio.productscomponent.domain.entities.categories.GenderCategory
import com.bitio.productscomponent.domain.entities.categories.ProductCategory

interface CategorizedProductsDao {

    suspend fun getProducts(categoryFilter: CategoryFilter): List<Product>

    suspend fun getOffers(categoryFilter: CategoryFilter): List<Product>

    suspend fun searchProduct(
        keyword: String,
        categoryFilter: CategoryFilter,
        tags: Set<String>
    ): List<Product>

    suspend fun getAllClothesCategories(): List<ProductCategory>
    suspend fun getAllGendersCategories(): List<GenderCategory>
    //suspend fun getProductBy

}