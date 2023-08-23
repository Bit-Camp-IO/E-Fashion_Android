package com.bitio.infrastructure.product.remote

import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiRetrofit : ProductsApi {

    @GET("product/list")
    override suspend fun getProductsByCategoryAndBrand(
        @Query("brands") brandId: String?,
        @Query("categories", encoded = true) categoryIds: List<String>?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ResponseWrapper<ProductsPage>

    @GET("product/{id}")
    override suspend fun getProductById(
        @Path("id") id: String
    ): ResponseWrapper<ProductResponse>

    @GET("category/list")
    override suspend fun getAllCategories(): ResponseWrapper<CategoryResponse>

    @GET("category/{id}")
    override suspend fun getCategoryById(@Path("id") id: String): CategoryResponse


}