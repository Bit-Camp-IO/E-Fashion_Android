package com.bitio.usercomponent.data.remote

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.entities.Address

interface UserApi {
    suspend fun getUserInformation(): ResponseWrapper<ProfileResponse>
    suspend fun updateUserInformation(userBody: UserBody): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun addUserImage(image: String)
    suspend fun addAddressOfUser(addressBody: AddressBody)
    suspend fun deleteAddressOfUser(addressId: String)
}