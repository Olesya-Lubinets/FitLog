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
) {
    fun toUI():FoodUI {
        return  FoodUI(
            food_id = food_id,
            food_name = food_name,
            food_type = food_type,
            food_url = food_url,
            servings = servings,
            isFavorite = false
        )
    }
}