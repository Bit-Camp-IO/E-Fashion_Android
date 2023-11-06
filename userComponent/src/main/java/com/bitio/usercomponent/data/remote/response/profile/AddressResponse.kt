package com.bitio.usercomponent.data.remote.response.profile

import com.bitio.usercomponent.domain.model.profile.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    override val id: String,
    override val isPrimary: Boolean,
    override val location: LocationResponse
) : Address
