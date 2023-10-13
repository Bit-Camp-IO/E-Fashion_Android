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
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZmE1YjUzMzdmOTk0NTE2ZGIyZjU5MSIsImlhdCI6MTY5NzIyMTQxNiwiZXhwIjoxNjk5ODEzNDE2fQ.n8kTnoBwMy5epYdEiI4F-6rc8Kr2yicoNQFEdwwTSGYelw44M6C_Mww4lbkp_l-S6s0vWkKXndV726YS4mvHG76Lbe0BQeZddiQKsab3s9EUQEJB5-4aFm4Rg4nOT92HCjmjsnKh3uT_EXz4-kS06jYw4NB_rMlNEIbTTRTIW_yvtzPJU00osSR2z31O_IdtpXF5814PHFYvSkbe20J41QaMkHqv0wFGK34q4J7iXQEydCyUSZfMbvTOXk1WlrJ2v7dfjTqtJKEm275kUFCZ8kXuWokBxTpiVg1bbBB2wzBj5tHe-v6dOks6tpmX7r6NzWViXyzliwZq06g0ErzBTQ")
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