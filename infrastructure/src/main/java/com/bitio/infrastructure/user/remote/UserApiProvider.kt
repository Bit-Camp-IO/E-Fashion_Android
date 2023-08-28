package com.bitio.infrastructure.user.remote


import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.response.UserResponse
import com.bitio.usercomponent.domain.entities.Address
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApiProvider : UserApi {

    @GET("user/me")
    override suspend fun getUserInformation(): ResponseWrapper<UserResponse>

    @GET("user/address")
    override suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>

    @Multipart
    @POST("user/profile-image")
    override suspend fun addUserImage(@Part image: String)

    @POST("user/address")
    override suspend fun addAddressOfUser(@Body addressBody: AddressBody)

    @DELETE("user/address/{address_id}")
    override suspend fun deleteAddressOfUser(@Path("address_id") addressId: String)

}