package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserDao {
    suspend fun saveUserInformation(user: User)
    fun getUserInformation(): Flow<User>
}