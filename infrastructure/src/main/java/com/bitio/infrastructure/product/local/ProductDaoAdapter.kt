package com.bitio.infrastructure.product.local

import androidx.room.Dao
import com.bitio.infrastructure.product.local.productModels.CategoryDto
import com.bitio.infrastructure.product.local.productModels.ProductDto
import com.bitio.infrastructure.product.local.room.ProductRoomDao
import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.domain.entities.categories.Category
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.productscomponent.domain.entities.products.Product

@Dao
class ProductDaoAdapter(private val roomDao: ProductRoomDao) : ProductDao {

    override suspend fun getFavoriteProducts(): List<Product> {
        return roomDao.getFavoriteProducts()
    }

    override suspend fun getFavoriteProductsId(): List<String> {
        return roomDao.getFavoriteProductsId()

    }

    override suspend fun addToFavoriteProducts(vararg product: Product) {
        val productsDb = product.map { ProductDto(it, true) }.toTypedArray()
        roomDao.addFavoriteProducts(*productsDb)
    }

    override suspend fun deleteFavoriteProduct(productId: String) {
        roomDao.deleteFavoriteProduct(productId)
    }

    override suspend fun deleteAllFavoriteProducts() {
        roomDao.deleteAllFavoriteProducts()
    }
    //

    override suspend fun getAllCategories(): List<Category> {
        return roomDao.getAllCategories()
    }

    override suspend fun getCategoriesByGender(genderType: GenderType): List<Category> {
        return roomDao.getCategoriesByGender(genderType)
    }

    override suspend fun addCategories(vararg category: Category) {
        val categories = category.map { it as CategoryDto }.toTypedArray()
        roomDao.addCategories(*categories)
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        roomDao.deleteCategoryById(categoryId)
    }

    override suspend fun deleteAllCategories() {
        roomDao.deleteAllCategory()
    }

}