package com.bitio.authcomponent.data.remote.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val message: String,
    val status: String,
    @SerialName(value = "data")
    val data: T?=null,
    val error: ApiError?=null
)


