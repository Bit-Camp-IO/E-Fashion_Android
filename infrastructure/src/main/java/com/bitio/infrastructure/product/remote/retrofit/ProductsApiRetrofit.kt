package com.bitio.infrastructure.product.remote.retrofit

import com.bitio.productscomponent.data.remote.request.CartItemBody
import com.bitio.productscomponent.data.remote.request.CartQuantityBody
import com.bitio.productscomponent.data.remote.request.IdBody
import com.bitio.productscomponent.data.remote.response.BrandResponse
import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.FavoriteProductResponse
import com.bitio.productscomponent.data.remote.response.OrderResponse
import com.bitio.productscomponent.data.remote.response.ProductDetailsResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.sharedcomponent.data.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiRetrofit {

    @GET("/api/product/list")
    suspend fun getProductsByCategoryAndBrand(
        @Query("brands") brandId: String?,
        @Query("categories") categoryIds: String?,
        @Query("discount") hasDiscount: Boolean?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ResponseWrapper<ProductsPage>

    @GET("/api/product/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ResponseWrapper<ProductDetailsResponse>

    @GET("/api/category/list")
    suspend fun getAllCategories(@Query("gender") genderId: Int?): ResponseWrapper<List<CategoryResponse>>

    @GET("/api/category/{id}")
    suspend fun getCategoryById(@Path("id") id: String): CategoryResponse

    @GET("/api/brand/list")
    suspend fun getBrands(): ResponseWrapper<List<BrandResponse>>

    @GET("/api/user/favorites")
    suspend fun getFavoriteProduct(): ResponseWrapper<List<FavoriteProductResponse>>

    @POST("/api/user/favorites")
    suspend fun addToFavoriteProduct(@Body id: IdBody)

    @HTTP(method = "DELETE", path = "/api/user/favorites", hasBody = true)
    suspend fun removeFromFavoriteProduct(@Body id: IdBody): Response<Unit>


    @GET("/api/user/cart")
    suspend fun getAllCarts(): ResponseWrapper<CartResponse>

    @POST("/api/user/cart")
    suspend fun addProductToCart(@Body cartItemBody: CartItemBody): ResponseWrapper<CartResponse>

    @HTTP(method = "DELETE", path = "/api/user/cart", hasBody = true)
    suspend fun deleteCart(@Body id: IdBody): ResponseWrapper<CartResponse>

    @HTTP(method = "PATCH", path = "/api/user/cart", hasBody = true)
    suspend fun editCart(
        @Body cartQuantityBody: CartQuantityBody
    ): ResponseWrapper<CartResponse>

    @GET("/api/order")
    suspend fun getAllOrders(): ResponseWrapper<List<OrderResponse>>
}
