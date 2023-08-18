package com.bitio.authcomponent.domain.entities

interface AuthData {
    val fullName:String
    val email:String
    val accessToken:String
    val refreshToken:String
}