package com.example.greentrip.utils

import com.example.greentrip.constants.Constants
import com.example.greentrip.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val dataStoreRepository: DataStoreRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = runBlocking {
            val token = dataStoreRepository.getToken(Constants.TOKEN)
            if (token != null) {
                originalRequest.newBuilder()
                    .header("Cookie", "jwt=$token")
                    .build()
            } else {
                originalRequest
            }
        }

        return chain.proceed(newRequest)
    }
}

