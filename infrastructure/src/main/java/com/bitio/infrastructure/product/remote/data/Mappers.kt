package com.bitio.infrastructure.product.remote.data

import com.bitio.sharedcomponent.data.ResponseWrapper

fun <T : Any, R : Any> ResponseWrapper<T>.transformData(transformer: (T) -> R): ResponseWrapper<R> {

    return if (data == null) ResponseWrapper(message, status, null, error) else
        ResponseWrapper(message, status, transformer(data!!), null)

}