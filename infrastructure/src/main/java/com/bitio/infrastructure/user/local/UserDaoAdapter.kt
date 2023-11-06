package com.bitio.infrastructure.user.local

import androidx.room.Dao
import com.bitio.infrastructure.user.local.room.UserProfileEntity
import com.bitio.infrastructure.user.local.room.UserRoomDao
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.domain.model.profile.UserProfile

@Dao
class UserDaoAdapter(
    private val userRoomDao: UserRoomDao
) : UserDao {
    override suspend fun saveUserInformation(userProfile: UserProfile) {
        val userEntity = UserProfileEntity(
            fullName = userProfile.fullName,
            email = userProfile.email,
            phoneNumber = userProfile.phoneNumber,
            profileImage = userProfile.profileImage
        )
        userRoomDao.deleteUserInfo()
        userRoomDao.saveUserInformation(userEntity)
    }

}