package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class AddressBody(
    override val id: String,
    override val isPrimary: Boolean,
    override val location: Location
) : Address
