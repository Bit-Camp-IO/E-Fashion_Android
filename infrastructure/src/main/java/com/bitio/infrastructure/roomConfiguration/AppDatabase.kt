package com.bitio.infrastructure.roomConfiguration

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitio.infrastructure.product.local.productModels.CategoryDto
import com.bitio.infrastructure.product.local.productModels.ProductDto
import com.bitio.infrastructure.product.local.room.ProductRoomDao

@Database(entities = [ProductDto::class, CategoryDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productRoomDao(): ProductRoomDao
}