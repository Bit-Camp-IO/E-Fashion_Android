package com.bitio.infrastructure.product.remote.retrofit

import com.bitio.sharedcomponent.data.ResponseWrapper
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ResponseWrapperConverter<T>(private val type: Type) : Converter<ResponseBody, ResponseWrapper<T>> {
    override fun convert(value: ResponseBody): ResponseWrapper<T> {
        val responseJson = value.string()
        return Json.decodeFromString(responseJson)
    }
}


class ResponseWrapperConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (getRawType(type) == ResponseWrapper::class.java) {
            return ResponseWrapperConverter<Any>(type)
        }
        return null
    }
}
