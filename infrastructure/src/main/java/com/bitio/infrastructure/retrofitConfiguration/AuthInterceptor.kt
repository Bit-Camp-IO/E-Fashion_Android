package com.bitio.infrastructure.retrofitConfiguration

import com.bitio.authcomponent.domain.useCases.GetAccessTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AuthInterceptor : Interceptor, KoinComponent {
    private val getAccessTokenUseCase: GetAccessTokenUseCase by inject()
    private var accessToken = getAccessTokenUseCase.getQuickAccessToken()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var currentJob: Job? = null

    init {
        observeToken()

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = createNewRequest(request, accessToken)
        var response = chain.proceed(newRequest)

        if (!response.isSuccessful && response.code == 401) {
            response.close()
            runBlocking(Dispatchers.IO) {
                try {
                    accessToken = getAccessTokenUseCase.invoke()

                    response = chain.proceed(createNewRequest(request, accessToken))
                } catch (e: Throwable) {

                }
            }
        }

        return response
    }

    private fun createNewRequest(request: Request, accessToken: String?): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }

    private fun observeToken() {
        currentJob = coroutineScope.launch {
            try {
                getAccessTokenUseCase.getAsStream().collect {
                    accessToken = it
                }
            } catch (e: Throwable) {
            }

        }
    }

}