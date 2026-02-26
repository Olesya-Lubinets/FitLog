package com.example.fitlog.data.api;

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
        private val authAPIService: AuthAPIService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

       TokenManager.fetchTokenIfNeeded(authAPIService)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${TokenManager.getToken()}")
            .build()
        Log.d("AuthTokenInterceptor", "Token added. REQUEST URL = ${request.url}")
        return chain.proceed(request)
    }
}