package com.bitio.usercomponent.data.local

import com.bitio.usercomponent.domain.model.User

data class UserEntity(
    override val email: String?,
    override val fullName: String?,
    override val phoneNumber: String?,
    override val profileImage: String?
) : User