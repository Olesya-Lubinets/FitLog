package com.example.fitlog.data.repository

import com.example.fitlog.data.api.FatSecretAPIClient
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse

class FoodRepository {
    val foodApi = FatSecretAPIClient.foodApi

    suspend fun getSearchedFood(searchedItem:String):FoodSearchResponse= foodApi.findFood(searchedItem)

    suspend fun getFoodByID(foodID:Long):FoodByIDResponse = foodApi.getByID(foodID)
}