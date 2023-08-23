package com.bitio.sharedcomponent.utils

import com.bitio.sharedcomponent.data.ApiErrorData
import com.bitio.sharedcomponent.data.ResponseWrapper

class ServerError(message: String) : Error(message )

fun <T> getDataOrThrowServerError(responseWrapper: ResponseWrapper<T>): T {
    val error: ApiErrorData? = responseWrapper.error
    if (error != null)
        throw (ServerError(responseWrapper.error.message))
    return responseWrapper.data!!
}