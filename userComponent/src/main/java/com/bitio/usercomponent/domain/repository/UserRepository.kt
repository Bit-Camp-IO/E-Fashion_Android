package com.bitio.usercomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.local.UserProfileEntity
import com.bitio.usercomponent.data.remote.response.ChatResponse
import com.bitio.usercomponent.data.remote.response.profile.AddressResponse
import com.bitio.usercomponent.data.remote.response.ChatStatusResponse
import com.bitio.usercomponent.data.remote.response.SenderChatResponse
import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import com.bitio.usercomponent.domain.model.Chat
import com.bitio.usercomponent.domain.model.profile.Location
import com.bitio.usercomponent.domain.model.profile.UserProfile
import com.bitio.usercomponent.domain.utils.ResponseStatus
import java.io.File

interface UserRepository {
    suspend fun refreshUserInfo(): ResponseStatus<UserProfileEntity>
    suspend fun getUserInformation(): ResponseWrapper<UserProfileResponse>
    fun getSavedUserInformation()
    suspend fun updateUserInformation(userProfile: UserProfile): ResponseWrapper<UserProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse>
    suspend fun addUserImage(file: File): ResponseWrapper<String>
    suspend fun addUserLocation(location: Location): ResponseWrapper<AddressResponse>
    suspend fun deleteUserLocation(addressId: String): ResponseWrapper<String>
    suspend fun getStatusChat(): String?
    suspend fun getAllMessages(chatId: String): List<Chat>
    suspend fun sendMessage(chatId: String, content: String): ResponseWrapper<SenderChatResponse>
}