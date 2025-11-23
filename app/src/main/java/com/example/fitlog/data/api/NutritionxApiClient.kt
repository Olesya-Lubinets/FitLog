package com.example.fitlog.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NutritionixApiClient {

    private const val APIKey = "aa192d38d8f444538b03b213bca0d353"
    private const val APIApplicationID = "012aac55d6914cb4a841a6aa42042d06"
    private const val BASE_URL = "https://platform.fatsecret.com/rest/"


    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-app-id", APIApplicationID)
            .addHeader("x-app-key", APIKey)
            .addHeader("x-remote-user-id", "0")
            .addHeader("Content-Type","application/json")
            .build()
        chain.proceed(request)
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val foodApi: FoodApiService = retrofit.create(FoodApiService::class.java)
    val workoutApi:WorkoutApiService = retrofit.create(WorkoutApiService::class.java)
}