package com.bitio.productscomponent.data.repository

import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.sharedcomponent.utils.getDataOrThrowServerError

class ProductRepositoryImpl(private val api: ProductsApi,private val dao: ProductDao) :
    ProductRepository {
    override suspend fun getProductsByCategoryAndBrand(
        brand: String?,
        categoriesIds: List<String>?,
        page: Int,
        limit: Int
    ): List<Product> {
        val response = api.getProductsByCategoryAndBrand(brand, categoriesIds, page, limit)
        return getDataOrThrowServerError(response).products
    }

    override suspend fun getProductsById(id: String): ProductDetails {
        TODO("Not yet implemented")
    }
}