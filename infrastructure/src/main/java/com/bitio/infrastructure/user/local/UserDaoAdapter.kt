package com.bitio.infrastructure.user.local

import androidx.room.Dao
import com.bitio.infrastructure.user.local.room.UserEntity
import com.bitio.infrastructure.user.local.room.UserRoomDao
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
class UserDaoAdapter(
    private val userRoomDao: UserRoomDao
) : UserDao {
    override suspend fun saveUserInformation(user: User) {
        val userEntity = UserEntity(
            fullName = user.fullName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            profileImage = user.profileImage
        )
        userRoomDao.deleteUserInfo()
        userRoomDao.saveUserInformation(userEntity)
    }

    override fun getUserInformation(): Flow<UserEntity> {
        return userRoomDao.getUserInformation()
    }
}