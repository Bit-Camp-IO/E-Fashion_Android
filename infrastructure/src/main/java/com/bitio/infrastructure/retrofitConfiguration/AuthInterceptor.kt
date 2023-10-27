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
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZmE1YjUzMzdmOTk0NTE2ZGIyZjU5MSIsImlhdCI6MTY5ODQyODUwOCwiZXhwIjoxNzAxMDIwNTA4fQ.w8GDoawiDiXKXUDbjWRBdobb4FNcIqkuj2m4AklZIQaVDp8Q4IGUr6eImz89814anCBv2AX9UA7I9q3Lfvb5en0FHqY3nhA0P6zER4CApCSeAF9JpxHo9k0LMekmvJaoc66hr6fHNFzbsJfDY35VnKve7lwMGN9COkHWPcaDY2uwUOpYVN2zsEJ_rA2iAPPosVBO4vXNdBfRD2_PJKzVxQyVBy1oSxz_Qu_6CLEe-PTfbJU8i-7uowj8UGZZNV4CEo5HHFZuAV-xn3WMESqCOpy3YB95mVGCF4QC3G9VmeztX7GSbfNi77PGPO_NuFVstp-Aq0ylw8QI8RGCjFwu0Q")
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