package com.bitio.productscomponent.data.local.dataSource

import com.bitio.productscomponent.domain.model.categories.Category
import com.bitio.productscomponent.domain.model.categories.GenderType
import com.bitio.productscomponent.domain.model.products.Product

interface ProductDao {

    suspend fun getFavoriteProducts(): List<Product>
    suspend fun getFavoriteProductsId(): List<String>
    suspend fun addToFavoriteProducts(vararg product: Product)
    suspend fun deleteFavoriteProduct(productId: String)
    suspend fun deleteAllFavoriteProducts()
    //
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoriesByGender(genderType: GenderType): List<Category>
    suspend fun addCategories(vararg category: Category)
    suspend fun deleteCategoryById(categoryId: String)
    suspend fun deleteAllCategories()
    //



}