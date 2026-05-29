package com.example.fitlog

import android.content.Context
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.model.FoodUI
import com.google.gson.Gson

object FoodTestData {

    private fun loadMockFoodItemFromFile(context: Context): FoodUI {
        val current_json = context.resources
            .openRawResource(R.raw.food_item_mock)
            .bufferedReader()
            .use { it.readText() }
        val foodByIDResponse = Gson().fromJson(current_json, FoodByIDResponse::class.java)
        return foodByIDResponse.food.toUI()
    }

    private fun loadFoodSearchResponsefromMock(context: Context): FoodSearchResponse {
        val current_json = context.resources
            .openRawResource(R.raw.food_mock)
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(current_json, FoodSearchResponse::class.java)
    }

}