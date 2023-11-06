package com.bitio.infrastructure.retrofitConfiguration

import com.bitio.authcomponent.domain.useCases.auth.GetAccessTokenUseCase
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
                    getAccessTokenUseCase()?.let {
                        accessToken = it
                    }
                    response = chain.proceed(createNewRequest(request, accessToken))
                } catch (e: Throwable) {


                }
            }
        }

        return response
    }

    private fun createNewRequest(request: Request, accessToken: String?): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZmE1YjUzMzdmOTk0NTE2ZGIyZjU5MSIsImlhdCI6MTY5OTE4Mzk1MywiZXhwIjoxNzAxNzc1OTUzfQ.OeGbeRybqvRCGbBcKgNX7zd6hgSWLdz5hRWFTueC70rFud3oFiDDx2oPfYJZR8VM02rFBDey8OHgJAe62z08BGwh9aLX0_xxi2cBVgvvs-Vmefu27Prus8EFFrmK2sobqxpPNle03FEXvERmKQuBnRoepcNpCrYTpq3Cqj5cZ1-yeNsoNQ_6MrA-9kzi9dP8VIzsj02RZ3jhEWUByT_Cfcs24kgVTMQo61TN8Qgk_PreN6TdFXfAZwwF0FdJlYioPHxRT3nylmZpEPngS5FagAjbLxx4IWycocmaR6E59Ov0pGI0p7mHE6WXlrhgqz6BpKMme6DU6hGhU_l21cXCFg")
            .build()
    }

    private  fun observeToken() {
        currentJob = coroutineScope.launch {
            try {
                getAccessTokenUseCase.getAsStream().collect { token ->
                    token?.let {
                        accessToken = it
                    }
                }
            } catch (e: Throwable) {
            }
        }
    }

}