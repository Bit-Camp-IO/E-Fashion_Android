package com.bitio.productscomponent.domain.repository

import com.bitio.productscomponent.domain.entities.Brand
import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {
    suspend fun getProductsByCategoryAndBrand(
        brand: String?, categoriesIds: List<String>?,
        hasDiscount: Boolean?, page: Int,
        limit: Int
    ): List<Product>

    suspend fun getProductsById(id: String): ProductDetails
    suspend fun getCategoriesByGender(genderType: GenderType): List<Category>
    suspend fun getBrands(): List<Brand>
    fun getFavoriteIdsFlow(): StateFlow<HashSet<String>>
    suspend fun addProductToFavorites(product: Product)
    suspend fun removeProductFromFavorite(id: String)


}