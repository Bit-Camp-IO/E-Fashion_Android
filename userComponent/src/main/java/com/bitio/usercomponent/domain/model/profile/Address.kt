package com.bitio.usercomponent.domain.model.profile

interface Address {
    val id: String
    val isPrimary: Boolean
    val location: Location
}