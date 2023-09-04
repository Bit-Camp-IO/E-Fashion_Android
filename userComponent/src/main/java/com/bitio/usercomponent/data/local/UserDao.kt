package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.entities.User
import kotlinx.coroutines.flow.Flow


interface UserDao {
    suspend fun saveUserInformation(user: User)
    fun getUserInformation(): Flow<User>
}