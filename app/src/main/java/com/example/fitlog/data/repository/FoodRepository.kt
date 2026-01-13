package com.example.fitlog.data.repository

import com.example.fitlog.data.api.FitSecretApiClient
import com.example.fitlog.data.model.FoodSearchResponse

class FoodRepository {
    val foodApi = FitSecretApiClient.foodApi

    suspend fun getSearchedFood(searchedItem:String):FoodSearchResponse= foodApi.findFood(searchedItem)
}