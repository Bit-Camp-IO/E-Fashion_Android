package com.bitio.usercomponent.domain.entities

interface Settings {
    val darkMode: String
    val language: String
    val addresses:List<Address>
}