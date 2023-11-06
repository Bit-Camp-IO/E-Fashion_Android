package com.bitio.usercomponent.data.remote

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.data.remote.request.UserProfileBody
import com.bitio.usercomponent.data.remote.response.ChatResponse
import com.bitio.usercomponent.data.remote.response.profile.AddressResponse
import com.bitio.usercomponent.data.remote.response.ChatStatusResponse
import com.bitio.usercomponent.data.remote.response.SenderChatResponse
import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import okhttp3.MultipartBody


interface UserApi {
    suspend fun getUserInformation(): ResponseWrapper<UserProfileResponse>
    suspend fun updateUserInformation(userBody: UserProfileBody): ResponseWrapper<UserProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse>
    suspend fun addUserImage(profileImage: MultipartBody.Part?): ResponseWrapper<String>
    suspend fun addUserLocation(locationBody: LocationBody): ResponseWrapper<AddressResponse>
    suspend fun deleteUserLocation(addressId: String): ResponseWrapper<String>


    suspend fun getStatusChat(): ResponseWrapper<ChatStatusResponse>
    suspend fun askNewChat(): ResponseWrapper<ChatStatusResponse>
    suspend fun getAllMessages(chatId: String): ResponseWrapper<List<ChatResponse>>
    suspend fun sendMessage(chatId: String, content: String): ResponseWrapper<SenderChatResponse>
}