package com.bitio.usercomponent.domain.repository

import com.bitio.sharedcomponent.data.ResponseWrapper
import com.bitio.usercomponent.data.local.UserDto
import com.bitio.usercomponent.data.remote.response.ProfileResponse
import com.bitio.usercomponent.domain.entities.Address
import com.bitio.usercomponent.domain.entities.User
import com.bitio.usercomponent.domain.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

interface UserRepository {
    suspend fun refreshUserInfo(): ResponseStatus<UserDto>
    fun getSavedUserInformation(): Flow<User>
    suspend fun updateUserInformation(user: User): ResponseWrapper<ProfileResponse>
    suspend fun getAddressesOfUser(): ResponseWrapper<List<Address>>
    suspend fun addUserImage(file: File): ResponseWrapper<String>
    suspend fun addAddressOfUser(address: Address)
    suspend fun deleteAddressOfUser(addressId: String)
}