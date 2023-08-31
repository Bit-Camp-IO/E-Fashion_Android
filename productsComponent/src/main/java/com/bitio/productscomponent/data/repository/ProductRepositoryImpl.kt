package com.bitio.productscomponent.data.repository

import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.domain.entities.Brand
import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.productscomponent.domain.repository.ProductRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(private val api: ProductsApi, private val dao: ProductDao) :
    ProductRepository {
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
        TODO("Not yet implemented")
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

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateFavoriteIds() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val favorites = api.getFavoritesList().data
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

    companion object {
        private var isLastFavoriteCallWasError = false
        private val favoriteProductsSet =
            MutableStateFlow<HashSet<String>>(hashSetOf())

    }

}