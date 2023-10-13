package com.bitio.productscomponent.data.remote

import com.bitio.productscomponent.data.remote.request.CartItemBody
import com.bitio.productscomponent.data.remote.request.IdBody
import com.bitio.productscomponent.data.remote.response.BrandResponse
import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.FavoriteProductResponse
import com.bitio.productscomponent.data.remote.response.ProductDetailsResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.flow.Flow


interface ProductsApi {

    suspend fun getProductsByCategoryAndBrand(
        brandId: String?,
        categoryIds: List<String>?,
        hasDiscount: Boolean?,
        page: Int,
        limit: Int
    ): ResponseWrapper<ProductsPage>

    suspend fun getProductDetailsById(id: String): ResponseWrapper<ProductDetailsResponse>
    suspend fun getAllCategories(genderType: GenderType): ResponseWrapper<List<CategoryResponse>>
    suspend fun getCategoryById(id: String): CategoryResponse
    suspend fun getBrands(): ResponseWrapper<List<BrandResponse>>
    suspend fun getFavoritesList(): ResponseWrapper<List<FavoriteProductResponse>>
    suspend fun addToFavoriteProduct(id: String)
    suspend fun removeProductFromFavorite(id: String)
    suspend fun getAllCarts(): ResponseWrapper<CartResponse>
    suspend fun addCart(cartItemBody: CartItemBody): ResponseWrapper<CartResponse>
    suspend fun deleteCart(id: IdBody): ResponseWrapper<CartResponse>
    suspend fun editCart(id: String, quantity: Int): ResponseWrapper<CartResponse>
}