package com.bitio.usercomponent.domain.model

interface Address {
    val id: String?
    val city: String?
    val state: String?
    val postalCode: Long?
    val isPrimary: Boolean?
}