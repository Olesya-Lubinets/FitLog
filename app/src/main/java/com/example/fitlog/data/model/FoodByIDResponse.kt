package com.example.fitlog.data.model

data class FoodByIDResponse(
    val food: FoodX
)

data class FoodX(
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String,
    val servings: Servings
)