package com.bitio.infrastructure.user.remote


import com.bitio.infrastructure.AuthInterceptor
import com.bitio.infrastructure.retrofitConfiguration.USER_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserService(
    private val authInterceptor: AuthInterceptor,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(authInterceptor)
    }.build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(USER_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: UserApiProvider = retrofit.create(UserApiProvider::class.java)
}
