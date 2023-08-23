package com.bitio.infrastructure.retrofitConfiguration

import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private var getAccessTokenUseCase: GetAccessTokenUseCase,updateAccessTokenUseCase: GetAccessTokenUseCase) : Interceptor {
    private var accessToken = ""
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var currentJob: Job? = null
    private var seed: Int = 0

    init {
      //  updateToken()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        val response = chain.proceed(newRequest)

        if (!response.isSuccessful && response.code == 401)
            updateToken()

        return response
    }

    private fun updateToken() {
//        currentJob?.cancel()
//        currentJob = coroutineScope.launch {
//            accessToken = getAccessTokenUseCase()
//            delay(DURATION_TO_UPDATE_ACCESS_TOKEN_MIN)
//
//        }

    }

}