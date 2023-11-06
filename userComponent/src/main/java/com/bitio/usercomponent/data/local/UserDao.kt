package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.model.profile.UserProfile


interface UserDao {
    suspend fun saveUserInformation(userProfile: UserProfile)
}