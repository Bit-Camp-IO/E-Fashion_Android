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
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5MzU2MjMwOCwiZXhwIjoxNjkzNTYyOTA4fQ.bnNyQGWgeRluczy0C3X0WrblgsQnCaphxQGpLN3aeU4FMourctY0-p3X9KeLxSMRnzP4_ANM3oISH1sqEymLHi_DtICyUJVnVacO8tl_BLjX7KleRS5LDV_2o-PS3JPiXw9uNrMnDTTDAjDMf6uLP-JOxHd3bu3fKsbA65L0htytaRv7-qCOxeyfG9nZzGiNNEt2tWFJBItYe7T2XgLZ9ERpUkbDip3JHFI6bcTE9r8KmhkmXULNi2soP5N3lEndjwq1qWDWJM4X-y_26aGVihHVoSbj1JlVUAY7UxNnqU1eEUlzBE7dq-gYfeDZ4J1VM3LQ-eUpOCZgztHXVK14mg"
    }
}