package com.bitio.infrastructure.user.remote


import android.util.Log
import com.bitio.infrastructure.retrofitConfiguration.AuthInterceptor
import com.bitio.infrastructure.retrofitConfiguration.USER_BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserService(
    private val authInterceptor: AuthInterceptor,
) {

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
