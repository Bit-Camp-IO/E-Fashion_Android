package com.bitio.sharedcomponent.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorData(
    val code: Int,
    override val message: String
) : Throwable()