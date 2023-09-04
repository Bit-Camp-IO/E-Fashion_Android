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
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5MzgwMjM0MywiZXhwIjoxNjkzODAyOTQzfQ.AM6-QAhLD8HHorKxT5Rd515vrQ3lmN__zWMmYO92ZiHePI6oyxodtp9RRG4yHX9Zcohy-95qqKPjDqdAzhi4NHOp6G5--YbTio4Cv-qmO8B4mYr86KmFKnwSb0f7VDzwhF9Co3KqxoS8qnxqiXJO4gcC2hMhHz-aIb6C75nUl2e2XmLdK9a_EifhIQUbKrlO6G_dHXNfyxmot1e7WER6RvUT_Jh6BMKoRV9EeTH3jk6PyQSXwB3rh1XNmBgclKH67dQabFaunDN7X03j5z2RnGCV-W3TkU3TEudKMzIfTSrhL3zq2sEHErPSYh-efBZIcj8Pmt1OYbCS6fJxxv-iWw"
    }
}