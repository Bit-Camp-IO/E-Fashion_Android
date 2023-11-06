package com.bitio.infrastructure.user.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoomDao {

    @Insert
    suspend fun saveUserInformation(userEntity: UserProfileEntity)

    @Query("SELECT * FROM user")
    fun getUserInformation(): Flow<UserProfileEntity>

    @Query("DELETE from user")
    suspend fun deleteUserInfo()

}