package com.bitio.usercomponent.data.remote

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.response.UserResponse
import com.bitio.usercomponent.domain.entities.Address

interface UserApi {
    suspend fun getUserInformation(): ResponseWrapper<UserResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun updateUserImage(image: String)
    suspend fun updateAddressOfUser(addressBody: AddressBody)
    suspend fun deleteAddressOfUser(addressId: String)
}