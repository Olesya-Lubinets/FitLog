package com.example.fitlog.data.api.workaoutAPI

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkoutTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", WorkoutToken.token)
            .build()
        return chain.proceed(request)
    }
}

object WorkoutRetrofit {

    private const val BASE_URL = "https://api.api-ninjas.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(WorkoutTokenInterceptor())
        .addInterceptor(logging)
        .build()

    val workoutAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WorkoutApiService::class.java)
    }
}