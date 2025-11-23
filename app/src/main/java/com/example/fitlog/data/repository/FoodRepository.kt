package com.example.fitlog.data.repository

import com.example.fitlog.data.api.NutritionixApiClient
import com.example.fitlog.data.model.FoodSearchResponse

class FoodRepository {
    val foodApi = NutritionixApiClient.foodApi

    suspend fun getSearchedFood(searchedItem:String):FoodSearchResponse= foodApi.findFood(searchedItem)
}