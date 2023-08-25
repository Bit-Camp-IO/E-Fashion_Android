package com.bitio.productscomponent.data.remote

import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.sharedcomponent.data.ResponseWrapper


interface ProductsApi {

    suspend fun getProductsByCategoryAndBrand(
        brandId: String?,
        categoryIds: List<String>?,
        page: Int,
        limit: Int
    ): ResponseWrapper<ProductsPage>

    suspend fun getProductById(id: String): ResponseWrapper<ProductResponse>
    suspend fun getAllCategories(genderId:Int?): ResponseWrapper<List<CategoryResponse>>
    suspend fun getCategoryById(id: String): CategoryResponse


}