package com.bitio.usercomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.entities.User

interface UserRepository {
    suspend fun getUserInformation(): ResponseWrapper<ProfileResponse>
    suspend fun updateUserInformation(user: User): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun addUserImage(image: String)
    suspend fun addAddressOfUser(address: Address)
    suspend fun deleteAddressOfUser(addressId: String)
}