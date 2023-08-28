package com.bitio.usercomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.response.UserResponse
import com.bitio.usercomponent.domain.entities.Address

interface UserRepository {
    suspend fun getUserInformation(): ResponseWrapper<UserResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun updateUserImage(image: String)
    suspend fun updateAddressOfUser(address: Address)
    suspend fun deleteAddressOfUser(addressId: String)
}