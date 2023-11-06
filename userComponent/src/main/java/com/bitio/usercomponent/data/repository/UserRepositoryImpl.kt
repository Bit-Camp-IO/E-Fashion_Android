package com.bitio.usercomponent.data.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.local.UserDao
import com.bitio.usercomponent.data.local.UserProfileEntity
import com.bitio.usercomponent.data.remote.RealtimeMessagingClient
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.data.remote.request.UserProfileBody
import com.bitio.usercomponent.data.remote.response.ChatResponse
import com.bitio.usercomponent.data.remote.response.profile.AddressResponse
import com.bitio.usercomponent.data.remote.response.ChatStatusResponse
import com.bitio.usercomponent.data.remote.response.SenderChatResponse
import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import com.bitio.usercomponent.domain.model.Chat
import com.bitio.usercomponent.domain.model.profile.Location
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.usercomponent.domain.repository.UserRepository
import com.bitio.usercomponent.domain.utils.ResponseStatus
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody


class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private var realtimeMessagingClient: RealtimeMessagingClient,
) : UserRepository {
    override suspend fun refreshUserInfo(): ResponseStatus<UserProfileEntity> {
        return try {
            val response = userApi.getUserInformation()
            if (response.data != null) {
                val userEntity = UserProfileEntity(
                    fullName = response.data?.fullName,
                    email = response.data?.email,
                    phoneNumber = response.data?.phoneNumber,
                    profileImage = response.data?.profileImage
                )
//                userDao.saveUserInformation(userEntity)
                ResponseStatus.Success(userEntity)
            } else {
                ResponseStatus.Error(response.message)
            }
        } catch (e: Throwable) {
            ResponseStatus.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getUserInformation(): ResponseWrapper<UserProfileResponse> {
        return userApi.getUserInformation()
    }

    override fun getSavedUserInformation() {

    }

    override suspend fun updateUserInformation(userProfile: UserProfile): ResponseWrapper<UserProfileResponse> {
        val user = UserProfileBody(
            email = userProfile.email,
            fullName = userProfile.fullName,
            phoneNumber = userProfile.phoneNumber
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

    override suspend fun getStatusChat(): String? {
        try {
            val response = userApi.getStatusChat()
            if (response.status == "success") {
                return response.data?.id
//                realtimeMessagingClient.connectSocketIo(response.data?.id.toString())
            } else {
                val aksResponse = userApi.askNewChat()
                if (aksResponse.status == "success") {
                    return response.data?.id
                }
            }
        } catch (e: Exception) {
            println("Sajjadio catch: ${e.message}")
        }

        return null
    }

    override suspend fun getAllMessages(chatId: String): List<Chat> {
        return userApi.getAllMessages(chatId).data!!
    }

    override suspend fun sendMessage(chatId: String, content: String): ResponseWrapper<SenderChatResponse> {
        return userApi.sendMessage(chatId, content)
    }

}