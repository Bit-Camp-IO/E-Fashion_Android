package com.bitio.usercomponent.data.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.response.UserResponse
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getUserInformation(): ResponseWrapper<UserResponse> {
        return userApi.getUserInformation()
    }

    override suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>> {
       return userApi.getAddressesOfUser()
    }

    override suspend fun addUserImage(image: String) {
        userApi.addUserImage(image)
    }

    override suspend fun addAddressOfUser(address: Address) {
        val addressBody = AddressBody(
            city = address.city,
            state = address.state,
            postalCode = address.postalCode,
            isPrimary = false
        )
        userApi.addAddressOfUser(addressBody)
    }

    override suspend fun deleteAddressOfUser(addressId: String) {
        userApi.deleteAddressOfUser(addressId)
    }

}