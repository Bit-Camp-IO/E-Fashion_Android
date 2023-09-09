package com.bitio.infrastructure.user.remote


import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.model.Address
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

    @GET("user/me")
    override suspend fun getUserInformation(): ResponseWrapper<ProfileResponse>

    @PATCH("user/me/edit")
    override suspend fun updateUserInformation(
        @Body userBody: UserBody
    ): ResponseWrapper<ProfileResponse>

    @GET("user/address")
    override suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>

    @Multipart
    @POST("user/profile-image")
    override suspend fun addUserImage(
        @Part profileImage: MultipartBody.Part?
    ): ResponseWrapper<String>

    @POST("user/address")
    override suspend fun addAddressOfUser(@Body addressBody: AddressBody)

    @DELETE("user/address/{address_id}")
    override suspend fun deleteAddressOfUser(@Path("address_id") addressId: String)

}