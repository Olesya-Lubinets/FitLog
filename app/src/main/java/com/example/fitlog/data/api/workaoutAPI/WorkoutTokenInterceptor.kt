package com.example.fitlog.data.api.workaoutAPI

import okhttp3.Interceptor
import okhttp3.Response

class WorkoutTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", WorkoutToken.token)
            .build()
        return chain.proceed(request)
    }
}