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
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5NDE5MjQxMCwiZXhwIjoxNjk0MTkzMDEwfQ.g-azhQuvD6MAZBk-j_K5X1jk2hi_t3sQGnUvvw4UbHKOljaF0VSJipahEiilm933HKzwV4_lsJLOHR4_tANSRDjbVDn0ZQu7-SJEvgpi81rXbjQZBRJPDnzQsOcQfHgGXuf0mS1EPmK4lszlc2EdXUU4yfPk-5iKmPborQrO-_9s3HC6tiEKiXuNshzhZzF5ewQim8tiaETOsD4No3IOJaugtjsS9sF2Ef6031PS_RHXxMWy03TJsHRFmGWHrCNvnvghAkAZxg9cLSOa1s9OfsEFJzj03vuIyI2i1xheD7afAyPBWwlHe86YAWb71NkbFWQ7C-FL3pxXl8aTdcJ_4w"
    }
}