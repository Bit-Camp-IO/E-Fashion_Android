package com.bitio.usercomponent.domain.entities

interface User {
    val id: String
    val fullName: String
    val profileImage: String
    val provider: String
    val isVerified:Boolean
    val address: String
    val settings: List<String>
    val addresses: List<Address>
}

