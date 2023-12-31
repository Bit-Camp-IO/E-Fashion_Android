package com.bitio.productscomponent.data.remote

import com.bitio.productscomponent.data.remote.request.CartItemBody
import com.bitio.productscomponent.data.remote.response.CartItemResponse
import com.bitio.productscomponent.data.remote.request.IdBody
import com.bitio.productscomponent.data.remote.response.BrandResponse
import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.OrderResponse
import com.bitio.productscomponent.data.remote.response.ProductDetailsResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.productscomponent.domain.model.categories.GenderType
import com.bitio.productscomponent.domain.model.favorites.Favorite
import com.bitio.sharedcomponent.data.ResponseWrapper


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
    suspend fun getFavoritesOfUser(): ResponseWrapper<List<Favorite>>
    suspend fun addToFavoriteProduct(id: String)
    suspend fun removeProductFromFavorite(id: String)
    suspend fun getAllProductsFromCart(): ResponseWrapper<CartResponse>
    suspend fun addProductToCart(cartItemBody: CartItemBody): ResponseWrapper<CartResponse>
    suspend fun deleteProductFromCart(id: IdBody): ResponseWrapper<CartResponse>
    suspend fun editProductOfCart(id: String, quantity: Int): ResponseWrapper<CartResponse>

    suspend fun getAllOrder(): ResponseWrapper<List<OrderResponse>>
}