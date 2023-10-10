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
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MGFlOTVjNGMxZDEyYWZiYmRiMGU2YSIsImlhdCI6MTY5NjkyNTUzOCwiZXhwIjoxNjk2OTI2MTM4fQ.e_IBDgO_kRoFmDd7lzM4csb4oE5OVirORMf2srDZZTlmpdcCiPsEtdd6AqsUyZeEnx59MY9aktOOFEksgbQ3zWvgolRVVinCRGByq8nf7EyWSPDDIO_qKq1Bu-j2IqjB2UaCKPK4n6O4xsbFVCRDjCEVJv5ruO25kkwCXF3NCEIZf2ZCP0E5wszF8fwZqdxVKRTayxg55AThJZDluT-cVuFaIPagRyQ6XIz2DLpS-Lb8Wq2JU7ny-kS83agP6AmX5AN1juLoJuDHG6qhdLyOe2lHSLUq7amM51cutZ9KdgDWsOIZK2z4LCXUt9oczbC69tdez7saXg_Z28V1TC1eSw")
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