package com.example.fitlog.data.api.foodAPI

import retrofit2.Retrofit


object FatSecretAPIClient {

    private val foodRetrofit:Retrofit = FoodRetrofit.foodAPI
    val foodApi: FoodApiService = foodRetrofit.create(FoodApiService::class.java)
}
