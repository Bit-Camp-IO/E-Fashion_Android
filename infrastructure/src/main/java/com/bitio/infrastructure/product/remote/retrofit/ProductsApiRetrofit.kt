package com.bitio.infrastructure.product.remote.retrofit

import com.bitio.productscomponent.data.remote.request.IdBody
import com.bitio.productscomponent.data.remote.response.BrandResponse
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.FavoriteProductResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiRetrofit {

    @GET("product/list")
    suspend fun getProductsByCategoryAndBrand(
        @Query("brands") brandId: String?,
        @Query("categories") categoryIds: String?,
        @Query("discount") hasDiscount: Boolean?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ResponseWrapper<ProductsPage>

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ResponseWrapper<ProductResponse>

    @GET("category/list")
    suspend fun getAllCategories(@Query("gender") genderId: Int?): ResponseWrapper<List<CategoryResponse>>

    @GET("category/{id}")
    suspend fun getCategoryById(@Path("id") id: String): CategoryResponse

    @GET("brand/list")
    suspend fun getBrands(): ResponseWrapper<List<BrandResponse>>

    @GET("user/favorites")
    suspend fun getFavoriteProduct(): ResponseWrapper<List<FavoriteProductResponse>>

    @POST("user/favorites")
    suspend fun addToFavoriteProduct(@Body id: IdBody)

    @HTTP(method = "DELETE", path = "user/favorites", hasBody = true)
    suspend fun removeFromFavoriteProduct(@Body id: IdBody): Response<Unit>


}
