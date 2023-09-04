package com.bitio.infrastructure.user.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInformation(userDto: UserDto)

    @Query("SELECT * FROM user")
    fun getUserInformation(): Flow<UserDto>

    @Query("DELETE from user")
    suspend fun deleteUserInfo()

}