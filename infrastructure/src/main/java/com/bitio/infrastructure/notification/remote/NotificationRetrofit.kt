package com.bitio.infrastructure.notification.remote


import com.bitio.infrastructure.product.remote.retrofit.LoggingInterceptor
import com.bitio.infrastructure.product.remote.retrofit.ProductRetrofit
import com.bitio.infrastructure.retrofitConfiguration.AuthInterceptor
import com.bitio.infrastructure.retrofitConfiguration.USER_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NotificationRetrofit {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(LoggingInterceptor())
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(USER_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: NotificationApiRetrofit = retrofit.create(NotificationApiRetrofit::class.java)
}
