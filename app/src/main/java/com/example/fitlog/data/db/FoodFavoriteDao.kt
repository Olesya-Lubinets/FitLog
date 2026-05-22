package com.example.fitlog.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodLog

@Dao
interface FoodFavoriteDao {
    @Query("SELECT * FROM FoodFavorite")
    suspend fun getAllOnce(): List<FoodFavorite>

    @Query("DELETE FROM FoodFavorite WHERE food_name=:deletedFoodFavoriteName")
    suspend fun delete(deletedFoodFavoriteName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newFoodFavorite: FoodFavorite)

    @Query("SELECT * FROM FoodFavorite WHERE food_id = :food_id LIMIT 1")
    suspend fun getByID(food_id:Long): FoodFavorite?
}



