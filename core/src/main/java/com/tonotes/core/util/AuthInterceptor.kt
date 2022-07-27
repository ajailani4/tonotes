package com.tonotes.core.util

import com.tonotes.core.domain.use_case.GetAccessTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val accessToken = runBlocking {
            getAccessTokenUseCase().first()
        }

        requestBuilder.addHeader("Authorization", "Bearer $accessToken")

        return chain.proceed(requestBuilder.build())
    }
}