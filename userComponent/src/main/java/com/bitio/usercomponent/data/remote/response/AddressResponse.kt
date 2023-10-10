package com.bitio.usercomponent.data.remote.response

import com.bitio.usercomponent.data.remote.request.LocationBody
import com.bitio.usercomponent.domain.model.Address
import com.bitio.usercomponent.domain.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    override val id: String,
    override val isPrimary: Boolean,
    override val location: LocationBody
) : Address
