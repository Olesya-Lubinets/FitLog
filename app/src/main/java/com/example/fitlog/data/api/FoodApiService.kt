package com.example.fitlog.data.api

import com.example.fitlog.data.model.FoodSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("v2/search/instant")
    suspend fun findFood(@Query("query") query: String): FoodSearchResponse
}