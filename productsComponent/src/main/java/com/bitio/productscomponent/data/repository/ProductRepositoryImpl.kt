package com.bitio.productscomponent.data.repository

import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.data.remote.request.CartItemBody
import com.bitio.productscomponent.data.remote.response.CartItemResponse
import com.bitio.productscomponent.data.remote.request.IdBody
import com.bitio.productscomponent.data.remote.response.CartResponse
import com.bitio.productscomponent.data.remote.response.OrderResponse
import com.bitio.productscomponent.domain.model.Brand
import com.bitio.productscomponent.domain.model.GeneralCart
import com.bitio.productscomponent.domain.model.categories.Category
import com.bitio.productscomponent.domain.model.categories.GenderType
import com.bitio.productscomponent.domain.model.favorites.Favorite
import com.bitio.productscomponent.domain.model.products.Product
import com.bitio.productscomponent.domain.model.products.ProductDetails
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val api: ProductsApi,
    private val dao: ProductDao
) : ProductRepository {

    init {
        updateFavoriteIds()
    }

    override suspend fun getProductsByCategoryAndBrand(
        brand: String?,
        categoriesIds: List<String>?, hasDiscount: Boolean?,
        page: Int,
        limit: Int
    ): List<Product> {
        return api.getProductsByCategoryAndBrand(
            brand,
            categoriesIds,
            hasDiscount,
            page,
            limit
        ).data!!.products

    }


    override suspend fun getProductsById(id: String): ProductDetails {
        return api.getProductDetailsById(id).data!!
    }

    override suspend fun getCategoriesByGender(genderType: GenderType): List<Category> {
        val response = api.getAllCategories(genderType)
        return response.data!!
    }

    override suspend fun getBrands(): List<Brand> {
        return api.getBrands().data!!
    }

    override fun getFavoriteIdsFlow(): StateFlow<HashSet<String>> {
        if (isLastFavoriteCallWasError)
            updateFavoriteIds()

        return favoriteProductsSet

    }

    override suspend fun addProductToFavorites(product: Product) {
        api.addToFavoriteProduct(product.id)
        addOrRemoveIdFromFavoriteStream(product.id)
        //   dao.addToFavoriteProducts(product)
    }

    override suspend fun removeProductFromFavorite(id: String) {
        api.removeProductFromFavorite(id)
        addOrRemoveIdFromFavoriteStream(id, false)
    }

    override suspend fun getAllProductsFromCart(): ResponseWrapper<CartResponse> {
        return api.getAllProductsFromCart()
    }

    override suspend fun addProductToCart(generalCart: GeneralCart): ResponseWrapper<CartResponse> {
        val cartItemBody =
            CartItemBody(
                id = generalCart.id,
                size = generalCart.size,
                color = generalCart.color,
                quantity = generalCart.quantity,
            )
        return api.addProductToCart(cartItemBody)
    }

    override suspend fun deleteProductFromCart(cartId: String): ResponseWrapper<CartResponse> {
        return api.deleteProductFromCart(IdBody(cartId))
    }

    override suspend fun editProductOfCart(
        cartId: String,
        quantity: Int
    ): ResponseWrapper<CartResponse> {
        return api.editProductOfCart(cartId, quantity)
    }

    override suspend fun getAllOrders(): ResponseWrapper<List<OrderResponse>> {
        return api.getAllOrder()
    }

    override suspend fun getFavoritesOfUser(): ResponseWrapper<List<Favorite>> {
        return api.getFavoritesOfUser()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateFavoriteIds() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val favorites = api.getFavoritesOfUser().data
                val set = favorites!!.map { it.id }.toHashSet()
                favoriteProductsSet.emit(set)
                isLastFavoriteCallWasError = false
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    isLastFavoriteCallWasError = true
                }
            }
        }

    }

    private fun addOrRemoveIdFromFavoriteStream(id: String, isAnAddOperation: Boolean = true) {
        val favSet: HashSet<String> = favoriteProductsSet.value.toHashSet()
        if (isAnAddOperation) favSet.add(id) else favSet.remove(id)
        favoriteProductsSet.value = favSet

    }


    private companion object {
        var isLastFavoriteCallWasError = false
        val favoriteProductsSet =
            MutableStateFlow<HashSet<String>>(hashSetOf())
    }

}
