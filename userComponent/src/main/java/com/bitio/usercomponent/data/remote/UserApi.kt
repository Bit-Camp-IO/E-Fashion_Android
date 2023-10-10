package com.bitio.usercomponent.data.remote

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.AddressResponse
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.model.Address
import okhttp3.MultipartBody


interface UserApi {
    suspend fun getUserInformation(): ResponseWrapper<ProfileResponse>
    suspend fun updateUserInformation(userBody: UserBody): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse>
    suspend fun addUserImage(profileImage: MultipartBody.Part?): ResponseWrapper<String>
    suspend fun addUserLocation(locationBody: LocationBody):ResponseWrapper<AddressResponse>
    suspend fun deleteUserLocation(addressId: String):ResponseWrapper<String>
}