package com.bitio.ui.profile.user

import com.bitio.usercomponent.domain.model.User

data class UserUiState(
    override val email: String? = null,
    override val fullName: String? = null,
    override val phoneNumber: String? = null,
    override val profileImage: String? = null
) : User
