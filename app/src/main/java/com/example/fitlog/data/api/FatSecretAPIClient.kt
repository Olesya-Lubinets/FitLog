package com.example.fitlog.data.api

import retrofit2.Retrofit


object FatSecretAPIClient {

    private val foodRetrofit:Retrofit = FoodRetrofit.foodAPI
    val foodApi: FoodApiService = foodRetrofit.create(FoodApiService::class.java)
    val workoutApi:WorkoutApiService = foodRetrofit.create(WorkoutApiService::class.java)
}
