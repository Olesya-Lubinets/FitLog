package com.example.fitlog.data.api

import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {

    @GET("rest/foods/search/v1")
    suspend fun findFood(
        @Query("search_expression") searchExpression: String,
        @Query("format") format: String = "json",
        @Query("page_number") pageNumber: Int = 0,
        @Query("max_results") maxResults: Int = 10
    ): FoodSearchResponse

    @GET("rest/food/v5")
    suspend fun getByID(
        @Query("food_id") food_id: Long,
        @Query("format") format: String = "json",
    ):FoodByIDResponse
}

