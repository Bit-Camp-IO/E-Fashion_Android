package com.bitio.usercomponent.domain.model

interface Address {
    val id: String
    val isPrimary: Boolean
    val location:Location
}