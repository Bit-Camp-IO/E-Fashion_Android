package com.bitio.sharedcomponent.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val message: String,
    val status: String,
    @SerialName(value = "data")
    val data: T?=null,
    val error: ApiErrorData?=null
)


