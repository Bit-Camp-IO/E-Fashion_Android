package com.bitio.infrastructure.roomConfiguration

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitio.infrastructure.product.local.productModels.CategoryDto
import com.bitio.infrastructure.product.local.productModels.ProductDto
import com.bitio.infrastructure.product.local.room.ProductRoomDao
import com.bitio.infrastructure.user.local.UserDto
import com.bitio.infrastructure.user.local.UserRoomDao
import com.bitio.usercomponent.data.local.UserDao

@Database(entities = [ProductDto::class, CategoryDto::class, UserDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productRoomDao(): ProductRoomDao
    abstract fun userRoomDao(): UserRoomDao
}