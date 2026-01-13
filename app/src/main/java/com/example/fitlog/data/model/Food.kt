package com.example.fitlog.data.model

data class Food(
    val brand_name: String?,
    val food_description: String,
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String
)

data class FoodsContainer(
    val food: List<Food>,
    val max_results: Int,
    val total_results: Int,
    val page_number: Int
)

data class FoodSearchResponse(
    val foods: FoodsContainer
)