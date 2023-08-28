package com.bitio.sharedcomponent.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val status: String,
    val message: String,
    @SerialName(value = "data")
    val data: T? = null,
    val error: ApiErrorData? = null
)


