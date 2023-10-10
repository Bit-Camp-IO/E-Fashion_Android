package com.bitio.usercomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.local.UserEntity
import com.bitio.usercomponent.data.remote.response.AddressResponse
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.model.Location
import com.bitio.usercomponent.domain.model.User
import com.bitio.usercomponent.domain.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    suspend fun refreshUserInfo(): ResponseStatus<UserEntity>
    fun getSavedUserInformation(): Flow<User>
    suspend fun updateUserInformation(user: User): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<AddressResponse>
    suspend fun addUserImage(file: File): ResponseWrapper<String>
    suspend fun addUserLocation(location: Location): ResponseWrapper<AddressResponse>
    suspend fun deleteAddressOfUser(addressId: String)
}