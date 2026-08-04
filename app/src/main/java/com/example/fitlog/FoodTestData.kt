package com.example.fitlog

import android.content.Context
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.model.FoodUI
import com.example.fitlog.data.model.FoodX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FoodTestData {
    private fun loadJson(fileName: String): String {
        return FoodTestData::class.java
            .classLoader!!
            .getResourceAsStream(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

     fun loadMockFoodItemFromFile(): FoodX {
        val current_json = loadJson("food_item_mock.json")
        val foodByIDResponse = Gson().fromJson(current_json, FoodByIDResponse::class.java)
        return foodByIDResponse.food
    }

     fun loadFoodSearchResponsefromMock(): FoodSearchResponse {
        val current_json = loadJson("food_mock.json")
        return Gson().fromJson(current_json, FoodSearchResponse::class.java)
    }

    fun loadFoodFavoriteListFromMock(): List<FoodFavorite> {
        val current_json = loadJson("foodFavorite_list_mock.json")
        return Gson().fromJson(current_json,object : TypeToken<List<FoodFavorite>>(){}.type)
    }

    fun getOneFavoriteFromMock():FoodFavorite = loadFoodFavoriteListFromMock().first()

}