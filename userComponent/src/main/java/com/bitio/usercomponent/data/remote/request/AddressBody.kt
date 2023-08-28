package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.entities.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressBody(
    override val id: String = "",
    override val city: String,
    override val state: String,
    override val postalCode: Long,
    override val isPrimary: Boolean
) : Address
