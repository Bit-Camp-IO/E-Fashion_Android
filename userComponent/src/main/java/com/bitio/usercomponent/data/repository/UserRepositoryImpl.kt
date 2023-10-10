package com.bitio.usercomponent.data.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.data.local.UserEntity
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.AddressResponse
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.model.Location
import com.bitio.usercomponent.domain.model.User
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody


class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun refreshUserInfo(): ResponseStatus<UserEntity> {
        return try {
            val response = userApi.getUserInformation()
            if (response.data != null) {
                val userEntity = UserEntity(
                    fullName = response.data?.fullName,
                    email = response.data?.email,
                    phoneNumber = response.data?.phoneNumber,
                    profileImage = response.data?.profileImage
                )
                userDao.saveUserInformation(userEntity)
                ResponseStatus.Success(userEntity)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }

    override  fun getSavedUserInformation(): Flow<User> {
        return userDao.getUserInformation()
    }

    override suspend fun updateUserInformation(user: User): ResponseWrapper<ProfileResponse> {
        val user = UserBody(
            email = user.email,
            fullName = user.fullName,
            phoneNumber = user.phoneNumber
        )
        return userApi.updateUserInformation(user)
    }

    override suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse> {
        return userApi.getAddressesOfUser()
    }

    override suspend fun addUserImage(file: File): ResponseWrapper<String> {
        val uploadFile = file.let {
            MultipartBody.Part.createFormData(
                "profile",
                file.name,
                it.asRequestBody()
            )
        }
        return userApi.addUserImage(uploadFile)
    }

    override suspend fun addUserLocation(location: Location): ResponseWrapper<AddressResponse> {
        val locationBody = LocationBody(
            latitude = location.latitude,
            longitude = location.longitude
        )
        return userApi.addUserLocation(locationBody)
    }

    override suspend fun deleteUserLocation(addressId: String): ResponseWrapper<String> {
        return userApi.deleteUserLocation(addressId)
    }

}