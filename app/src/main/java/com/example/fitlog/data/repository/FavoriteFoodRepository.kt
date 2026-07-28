package com.example.fitlog.data.repository

import com.example.fitlog.data.db.FoodFavoriteDao
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodFavoriteRepository @Inject constructor(private val foodFavoriteDao: FoodFavoriteDao) {

    val  foodFavoriteFlow: Flow<List<FoodFavorite>> = foodFavoriteDao.getAll()

    suspend fun insert(newFoodFavorite: FoodFavorite) = foodFavoriteDao.insert(newFoodFavorite)
    suspend fun delete(deletedFoodFavoriteName: String)  = foodFavoriteDao.delete(deletedFoodFavoriteName)
    suspend fun getAllOnce():List<FoodFavorite> = foodFavoriteDao.getAllOnce()
    suspend fun getById(food_id:Long): FoodFavorite?  = foodFavoriteDao.getByID(food_id)
    }
