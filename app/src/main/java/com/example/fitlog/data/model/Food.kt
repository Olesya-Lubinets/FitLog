package com.example.fitlog.data.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

data class Food(
    val brand_name: String?,
    val food_description: String?,
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String
) {
    fun toUi(isInFavorites: Boolean) = FoodUI(brand_name = brand_name, food_description = food_description,
        food_id = food_id, food_name=food_name,food_type =food_type ,
        food_url = food_url, isFavorite = isInFavorites)
}

data class FoodsContainer(
    val food: List<Food>,
    val max_results: Int,
    val total_results: Int,
    val page_number: Int
)

data class FoodSearchResponse(
    val foods: FoodsContainer
)

@Entity
data class FoodFavorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand_name: String?,
    val food_description: String?,
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String,
    val servings: Servings
) {  fun toUi() = FoodUI(brand_name = brand_name, food_description = food_description,
    food_id = food_id, food_name=food_name,food_type = food_type , servings = servings,
    food_url = food_url, isFavorite = true)
}

data class FoodUI (
    val brand_name: String? = null,
    val food_description: String? = null,
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String,
    val servings: Servings?= null,
    val isFavorite: Boolean
) {
    fun toFavorite(servings: Servings) = FoodFavorite(
        brand_name = brand_name,
        food_description = food_description,
        food_id = food_id,
        food_name = food_name,
        food_type = food_type,
        food_url = food_url,
        servings = servings
    )
    @SuppressLint("NewApi")
    fun toLog(calories:Int) = FoodLog(
        date = LocalDate.now(),
        name = food_name,
        calories = calories
    )
}

