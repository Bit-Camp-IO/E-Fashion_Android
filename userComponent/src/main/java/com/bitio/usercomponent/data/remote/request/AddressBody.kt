package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.model.profile.Address
import com.bitio.usercomponent.domain.model.profile.Location
import kotlinx.serialization.Serializable

@Serializable
data class AddressBody(
    override val id: String,
    override val isPrimary: Boolean,
    override val location: Location
) : Address
