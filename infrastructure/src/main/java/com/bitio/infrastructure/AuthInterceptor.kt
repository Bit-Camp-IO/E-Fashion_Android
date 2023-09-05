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
        const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZWNlNzQ3MDA5OTA0NWY0NDNkMjM0MCIsImlhdCI6MTY5MzkyNjE0OCwiZXhwIjoxNjkzOTI2NzQ4fQ.jZOpOgJ4YJWXYj73C-OgU-VPj7iyT26iYKoJ1kXyVbCmbl4v3XZ6knvrVilarAOIOXgEO5GF2TenblvCCBXpuwqiytCcq9hi7JceBmOjU-pToFOs5RRxcI-Ess20YdLLy7lKobOGNQSOkVaz5gA265R-3ByOjBWjeMO-cCsd5YNqMvuKyWSvJv4grn680fjE9Mp6yiinve_xInKvZHbHc5U2gAzFFbMFSvCqxGBAwdwvaV9AbjN9LqcU0_2P3UBvr_ObRoOh9djLBvt1Q5UM6UzX2muLoXbjBZaEEIOopG9Yr9Gr1T8S-IA409yXMljH6RhmT_F7W3lTEFExmd5nzg"
    }
}