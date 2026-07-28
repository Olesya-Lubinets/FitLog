package com.example.fitlog.data.api.authenticationFoodApi

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class AuthCredentialsInterceptor(private val basicAuth: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request().newBuilder()
                .addHeader("Authorization", basicAuth)
                .build()
        Log.d("AuthCredentialsInterceptor", "Credential for getting token have been added REQUEST URL = ${request.url}")
        return chain.proceed(request)
    }
}