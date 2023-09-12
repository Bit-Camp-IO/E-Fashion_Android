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
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5NDUzMTM3MiwiZXhwIjoxNjk0NTMxOTcyfQ.0Ecobz4YjkLIo0YcqgDI__8Z1VpBvpFlKAZc1cmdcY4VynlY6oVW3AyoDc_YTIEMfzj9OjPi0dxb1bUttomyo27UGTbKesPRReHjeJ08WCFCR6aL7wLFnJaj5EQN5_caroxgno0X8RyCuLyibP5Ygd2PRghVUrm7R9XW0CO8b8fXwbPyLGPwzLH8FiBs21iFX9jrhxP1Cmk-wipyY_ARHpxloQII7BHIynm78wN6Xt0Z4-voimVtr58Qhp64ltQfli5o8dgkQQIaE0MklbJkFIKgIjiv19yhqQ7r-WBMOdkG6vP2FlRc38vgiJYQdsriye4fK6nMmwtKf_Lvl8y3kA"
    }
}