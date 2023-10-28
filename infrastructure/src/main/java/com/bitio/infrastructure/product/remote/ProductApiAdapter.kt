package com.bitio.infrastructure.product.remote

import android.util.Log
import com.bitio.infrastructure.product.remote.retrofit.ProductsApiRetrofit
import com.bitio.infrastructure.retrofitConfiguration.ErrorWrapper
import com.bitio.productscomponent.data.remote.ProductsApi
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
import com.bitio.productscomponent.domain.entities.cart.CartItem
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class ProductApiAdapter(private val retrofitApi: ProductsApiRetrofit) : ProductsApi {
    override suspend fun getProductsByCategoryAndBrand(
        brandId: String?,
        categoryIds: List<String>?,
        hasDiscount: Boolean?,
        page: Int,
        limit: Int
    ): ResponseWrapper<ProductsPage> {
        Log.d("aaa", (hasDiscount).toString())

        try {
            val parsedCategories = categoryIds?.joinToString(",")
            return retrofitApi.getProductsByCategoryAndBrand(
                brandId,
                parsedCategories,
                hasDiscount,
                page,
                limit
            )
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun getProductDetailsById(id: String): ResponseWrapper<ProductDetailsResponse> {
        try {
            return retrofitApi.getProductById(id)
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun getAllCategories(genderType: GenderType): ResponseWrapper<List<CategoryResponse>> {

        try {
            return retrofitApi.getAllCategories(genderType.id)
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun getCategoryById(id: String): CategoryResponse {
        try {
            return retrofitApi.getCategoryById(id)
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }

    }

    override suspend fun getBrands(): ResponseWrapper<List<BrandResponse>> {
        try {
            return retrofitApi.getBrands()
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun getFavoritesList(): ResponseWrapper<List<FavoriteProductResponse>> {
        try {
            return retrofitApi.getFavoriteProduct()
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }

    }

    override suspend fun addToFavoriteProduct(id: String) {

        try {
            retrofitApi.addToFavoriteProduct(IdBody(id))
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }

    }

    override suspend fun removeProductFromFavorite(id: String) {
        retrofitApi.removeFromFavoriteProduct(IdBody(id))
    }

    override suspend fun getAllProductsFromCart(): ResponseWrapper<CartResponse> {
        try {
            return retrofitApi.getAllCarts()
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun addProductToCart(cartItemBody: CartItemBody): ResponseWrapper<CartResponse> {
        try {
            return retrofitApi.addCart(cartItemBody)
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }


    override suspend fun deleteProductFromCart(id: IdBody): ResponseWrapper<CartResponse> {
        try {
            return retrofitApi.deleteCart(id)
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override  suspend fun editProductOfCart(id: String, quantity:Int): ResponseWrapper<CartResponse> {
        try {
            return retrofitApi.editCart(CartQuantityBody(id,quantity))
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }

    override suspend fun getAllOrder(): ResponseWrapper<List<OrderResponse>> {
        try {
            return retrofitApi.getAllOrders()
        } catch (e: HttpException) {
            throw parseIfApiErrorException(e).error
        }
    }
}


fun parseIfApiErrorException(e: HttpException): ErrorWrapper {
    try {
        val errorResponseBody = e.response()!!.errorBody()
        val errorResponseJson = errorResponseBody!!.string()
        return Json.decodeFromString(errorResponseJson)
    } catch (_: Throwable) {
        Log.d("xxxxx", "xxxxx")
        throw e
    }


}
