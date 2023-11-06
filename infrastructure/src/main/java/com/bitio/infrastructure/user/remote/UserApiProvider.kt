package com.bitio.infrastructure.user.remote


import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.data.remote.request.UserProfileBody
import com.bitio.usercomponent.data.remote.response.ChatResponse
import com.bitio.usercomponent.data.remote.response.profile.AddressResponse
import com.bitio.usercomponent.data.remote.response.ChatStatusResponse
import com.bitio.usercomponent.data.remote.response.SenderChatResponse
import com.bitio.usercomponent.data.remote.response.profile.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApiProvider : UserApi {

    @GET("/api/user/me")
    override suspend fun getUserInformation(): ResponseWrapper<UserProfileResponse>

    @PATCH("/api/user/me/edit")
    override suspend fun updateUserInformation(
        @Body userBody: UserProfileBody
    ): ResponseWrapper<UserProfileResponse>

    @GET("/api/user/address")
    override suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse>

    @Multipart
    @POST("/api/user/profile-image")
    override suspend fun addUserImage(
        @Part profileImage: MultipartBody.Part?
    ): ResponseWrapper<String>

    @POST("/api/user/address")
    override suspend fun addUserLocation(@Body locationBody: LocationBody): ResponseWrapper<AddressResponse>

    @DELETE("/api/user/address/{address_id}")
    override suspend fun deleteUserLocation(@Path("address_id") addressId: String): ResponseWrapper<String>

    @POST("/api/chat/new-chat")
    override suspend fun askNewChat(): ResponseWrapper<ChatStatusResponse>

    @GET("/api/chat")
    override suspend fun getStatusChat(): ResponseWrapper<ChatStatusResponse>

    @GET("/api/chat/{chat_id}/messages")
    override suspend fun getAllMessages(@Path("chat_id") chatId: String): ResponseWrapper<List<ChatResponse>>

    @POST("/api/chat/{chat_id}/new-message")
    override suspend fun sendMessage(
        @Path("chat_id") chatId: String,
        @Body content: String
    ): ResponseWrapper<SenderChatResponse>

}