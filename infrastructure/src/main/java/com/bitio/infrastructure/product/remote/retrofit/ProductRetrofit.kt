package com.bitio.infrastructure.product.remote.retrofit


import com.bitio.infrastructure.retrofitConfiguration.USER_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


object ProductRetrofit {
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(USER_BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    val service: ProductsApiRetrofit = retrofit.create(ProductsApiRetrofit::class.java)
}
