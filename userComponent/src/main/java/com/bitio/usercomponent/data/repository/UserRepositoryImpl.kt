package com.bitio.usercomponent.data.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.remote.UserApi
import com.bitio.usercomponent.data.remote.request.AddressBody
import com.bitio.usercomponent.data.remote.request.UserBody
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.entities.User
import com.bitio.usercomponent.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getUserInformation(): ResponseWrapper<ProfileResponse> {
        return userApi.getUserInformation()
    }

    override suspend fun updateUserInformation(user: User): ResponseWrapper<ProfileResponse> {
        val user = UserBody(
            email = user.email,
            fullName = user.fullName,
            phoneNumber = user.phoneNumber
        )
        return userApi.updateUserInformation(user)
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