package com.bitio.authcomponent.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(val code: Int, val message: String)