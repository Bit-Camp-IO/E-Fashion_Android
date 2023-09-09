package com.bitio.usercomponent.data.remote.request

import com.bitio.usercomponent.domain.model.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressBody(
    override val id: String? = null,
    override val city: String? = null,
    override val state: String? = null,
    override val postalCode: Long? = null,
    override val isPrimary: Boolean? = null
) : Address
