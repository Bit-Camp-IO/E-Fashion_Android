package com.bitio.infrastructure.product.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bitio.infrastructure.product.local.productModels.CategoryDto
import com.bitio.infrastructure.product.local.productModels.ProductDto
import com.bitio.productscomponent.domain.entities.categories.GenderType


@Dao
interface ProductRoomDao {
    @Query("SELECT * FROM products Where isFavorite = 1 ")
    fun getFavoriteProducts(): List<ProductDto>

    @Query("SELECT id FROM products Where isFavorite = 1 ")
    fun getFavoriteProductsId(): List<String>

    @Insert
    fun addFavoriteProducts(vararg productDto: ProductDto)

    @Query("DELETE FROM products WHERE id = :productId")
    fun deleteFavoriteProduct(productId: String)

    @Query("DELETE  FROM products WHERE isFavorite = 1")
    fun deleteAllFavoriteProducts()

    //
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryDto>

    @Query("SELECT * FROM categories WHERE genderType = :genderType")
    fun getCategoriesByGender(genderType: GenderType): List<CategoryDto>

    @Insert
    fun addCategories(vararg categoryDto: CategoryDto)

    @Query("DELETE FROM categories WHERE id = :categoryId")
    fun deleteCategoryById(categoryId: String)

    @Query("Delete From Categories")
    fun deleteAllCategory()

}