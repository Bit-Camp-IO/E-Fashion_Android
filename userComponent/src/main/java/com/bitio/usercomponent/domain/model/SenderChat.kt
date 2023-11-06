package com.bitio.usercomponent.domain.model

interface SenderChat {
    val content: String
    val date: String
    val sender: String
    val me: Boolean
}