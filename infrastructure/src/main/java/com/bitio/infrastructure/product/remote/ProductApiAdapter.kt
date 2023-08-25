package com.bitio.infrastructure.product.remote

import com.bitio.infrastructure.product.remote.data.transformData
import com.bitio.infrastructure.product.remote.retrofit.ProductsApiRetrofit
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.data.remote.response.CategoryResponse
import com.bitio.productscomponent.data.remote.response.ProductResponse
import com.bitio.productscomponent.data.remote.response.ProductsPage
import com.bitio.sharedcomponent.data.ResponseWrapper

class ProductApiAdapter(private val retrofitApi: ProductsApiRetrofit) : ProductsApi {
    override suspend fun getProductsByCategoryAndBrand(
        brandId: String?,
        categoryIds: List<String>?,
        page: Int,
        limit: Int
    ): ResponseWrapper<ProductsPage> {
        return retrofitApi.getProductsByCategoryAndBrand(brandId, categoryIds, page, limit)
    }

    override suspend fun getProductById(id: String): ResponseWrapper<ProductResponse> {
        return retrofitApi.getProductById(id)
    }

    override suspend fun getAllCategories(genderId: Int?): ResponseWrapper<List<CategoryResponse>> {
       return  retrofitApi.getAllCategories(genderId).transformData { it.map { it.toCategoryResponse() } }

    }

    override suspend fun getCategoryById(id: String): CategoryResponse {
        return retrofitApi.getCategoryById(id)
    }
}

