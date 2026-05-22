package com.example.fitlog.data.repository

import com.example.fitlog.data.db.FoodFavoriteDao
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodLog

class FoodFavoriteRepository(private val foodFavoriteDao: FoodFavoriteDao) {

    suspend fun insert(newFoodFavorite: FoodFavorite) = foodFavoriteDao.insert(newFoodFavorite)
    suspend fun delete(deletedFoodFavoriteName: String)  = foodFavoriteDao.delete(deletedFoodFavoriteName)
    suspend fun getAllOnce():List<FoodFavorite> = foodFavoriteDao.getAllOnce()
    suspend fun getById(food_id:Long): FoodFavorite?  = foodFavoriteDao.getByID(food_id)
    }
