package com.example.fitlog.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodRetrofit {

    private const val BASE_URL = "https://platform.fatsecret.com/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttp = OkHttpClient.Builder()
        .addInterceptor(AuthTokenInterceptor(AuthRetrofit.authAPI))
        .addInterceptor(logging)
        .build()

    val foodAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}