package com.bitio.usercomponent.domain.entities

interface Address {
    val id: String
    val city: String
    val state: String
    val postalCode: Long
    val isPrimary: Boolean
}