package com.andback.pocketfridge.present.utils

import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class XAccessTokenInterceptor @Inject constructor(
    private val readDataStoreUseCase: ReadDataStoreUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token = runBlocking {
            readDataStoreUseCase.execute("JWT") ?: ""
        }

        val request = chain.request().newBuilder()
            .addHeader("JWT", token)
            .build()

        return chain.proceed(request)
    }
}