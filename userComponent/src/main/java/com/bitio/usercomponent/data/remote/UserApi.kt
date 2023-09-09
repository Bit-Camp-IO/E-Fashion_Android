package com.bitio.usercomponent.data.remote

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.model.Address
import okhttp3.MultipartBody


interface UserApi {
    suspend fun getUserInformation(): ResponseWrapper<ProfileResponse>
    suspend fun updateUserInformation(userBody: UserBody): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun addUserImage(profileImage: MultipartBody.Part?): ResponseWrapper<String>
    suspend fun addAddressOfUser(addressBody: AddressBody)
    suspend fun deleteAddressOfUser(addressId: String)
}