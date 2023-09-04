package com.bitio.infrastructure.user.local

import androidx.room.Dao
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.domain.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
class UserDaoAdapter(
    private val userRoomDao: UserRoomDao
) : UserDao {
    override suspend fun saveUserInformation(user: User) {
        val userDto = UserDto(
            fullName = user.fullName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            profileImage = user.profileImage
        )
        userRoomDao.deleteUserInfo()
        userRoomDao.saveUserInformation(userDto)
    }

    override fun getUserInformation(): Flow<UserDto> {
        return userRoomDao.getUserInformation()
    }
}