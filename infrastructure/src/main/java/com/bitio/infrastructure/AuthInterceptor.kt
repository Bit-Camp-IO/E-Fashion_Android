package com.bitio.infrastructure

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $TOKEN")
            .build()
        return chain.proceed(modifiedRequest)
    }

    private companion object {
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5NDIyNTc1MywiZXhwIjoxNjk0MjI2MzUzfQ.ERWmJDocxL_4FOS0Pbmg6RjXaQBsSadEWUkTpUcB__Nzfe5SGVYIFk_GITKDrIl4niMff9AL48OBiJ6T7xYf_urjAXOpF4c7E-2J2sJiY9RQI1SmqKHW9uCWLtiOk0tsg_nYcCXV385Si7PTnhWm_ECe_U_q-1hQW-9cg2VkmNcDYPINS-vTdNfaMNI18FYkvTqFapyNToczg6SRBr7N757oKDk1PZA7vfM2M_T6Jb77goHpu_7qq1e9r7BmEuTfBJtau2XeWicOxLz9PJxkDI_ooGbgVbAVVu3rbkroa6XA1vH_4cmfE06iqnrwhNoaj-jc90aPEDpQOOsAWm21aA"
    }
}