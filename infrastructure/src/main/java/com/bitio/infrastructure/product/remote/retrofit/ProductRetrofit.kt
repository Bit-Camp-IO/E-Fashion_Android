package com.bitio.infrastructure.product.remote.retrofit


import android.util.Log
import com.bitio.infrastructure.retrofitConfiguration.AuthInterceptor
import com.bitio.infrastructure.retrofitConfiguration.ServerErrorInterceptor
import com.bitio.infrastructure.retrofitConfiguration.USER_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

object ProductRetrofit {

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
     //   .addInterceptor(ServerErrorInterceptor())
        .addInterceptor(LoggingInterceptor())
        .build()
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(USER_BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    val service: ProductsApiRetrofit = retrofit.create(ProductsApiRetrofit::class.java)
}

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("Interceptor", "Sending request ${request.url}")

        val response = chain.proceed(request)

        Log.d("Interceptor", "Got response ${request.url.toString()+"  "+ response.code}")
        return response
    }
}
