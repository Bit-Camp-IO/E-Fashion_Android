package com.bitio.infrastructure.retrofitConfiguration

import android.util.Log
import com.bitio.sharedcomponent.data.ApiErrorData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class ServerErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {

            val errorResponseBody = response.peekBody(Long.MAX_VALUE)
            if (errorResponseBody != null) {
                Log.d("xxxxx Error Interceptor", parseErrorResponse(errorResponseBody).toString())
            }
        }

        return response
    }

    private fun parseErrorResponse(responseBody: ResponseBody): ApiErrorData {
        val errorResponseJson = responseBody.string()
        val errorResponse = Json.decodeFromString<ErrorWrapper>(errorResponseJson)
//        val errorCode = errorResponse.error.code
//        val errorMessage = errorResponse.error.message
        //ApiErrorData(errorCode, errorMessage)
        return errorResponse.error
    }
}

@Serializable
data class ErrorWrapper(
    val message: String,
    val status: String,
    val error: ApiErrorData
)

