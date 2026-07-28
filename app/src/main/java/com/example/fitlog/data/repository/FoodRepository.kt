package com.example.fitlog.data.repository

import com.example.fitlog.data.api.foodAPI.FoodApiService
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.model.Servings
import javax.inject.Inject

class FoodRepository @Inject constructor(private val foodApi: FoodApiService) {

    suspend fun getSearchedFood(searchedItem:String):FoodSearchResponse {
        val result = foodApi.findFood(searchedItem)
        if (result.foods.food.isEmpty()) throw NoSuchItemException("")
        else return result
    }

    suspend fun getFoodByID(foodID:Long):FoodByIDResponse = foodApi.getByID(foodID)
    suspend fun getFoodServingsByID(foodID:Long):Servings = foodApi.getByID(foodID).food.servings
}

