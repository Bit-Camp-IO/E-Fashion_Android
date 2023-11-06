package com.bitio.usercomponent.domain.model

interface Chat {
    val content: String
    val date: String
    val me: Boolean
}