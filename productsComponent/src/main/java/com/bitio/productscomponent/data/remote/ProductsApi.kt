package com.bitio.productscomponent.data.remote

import com.bitio.productscomponent.data.remote.response.BrandResponse
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.FavoriteProductResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.sharedcomponent.data.ResponseWrapper


interface ProductsApi {

    suspend fun getProductsByCategoryAndBrand(
        brandId: String?,
        categoryIds: List<String>?,
        hasDiscount:Boolean?,
        page: Int,
        limit: Int
    ): ResponseWrapper<ProductsPage>

    suspend fun getProductById(id: String): ResponseWrapper<ProductResponse>
    suspend fun getAllCategories(genderType: GenderType): ResponseWrapper<List<CategoryResponse>>
    suspend fun getCategoryById(id: String): CategoryResponse
    suspend fun getBrands(): ResponseWrapper<List<BrandResponse>>
    suspend fun getFavoritesList(): ResponseWrapper<List<FavoriteProductResponse>>
    suspend fun addToFavoriteProduct(id: String)
    suspend fun removeProductFromFavorite(id: String)


}