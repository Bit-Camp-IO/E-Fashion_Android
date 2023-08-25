package com.bitio.infrastructure.product.remote.retrofit

import com.bitio.infrastructure.product.remote.data.CategoryAdaptee
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiRetrofit  {

    @GET("product/list")
     suspend fun getProductsByCategoryAndBrand(
        @Query("brands") brandId: String?,
        @Query("categories", encoded = true) categoryIds: List<String>?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ResponseWrapper<ProductsPage>

    @GET("product/{id}")
     suspend fun getProductById(
        @Path("id") id: String
    ): ResponseWrapper<ProductResponse>

    @GET("category/list")
     suspend fun getAllCategories(@Query("gender") genderId:Int?): ResponseWrapper<List<CategoryAdaptee>>

    @GET("category/{id}")
     suspend fun getCategoryById(@Path("id") id: String): CategoryResponse


}