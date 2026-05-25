package com.example.fitlog.data.repository

import com.example.fitlog.data.db.FoodLogDao
import com.example.fitlog.data.model.FoodLog
import kotlinx.coroutines.flow.Flow

class FoodLogRepository(private val foodLogDao: FoodLogDao) {

    val foodLogFlow: Flow<List<FoodLog>> = foodLogDao.getAll()

    suspend fun insert(newFoodLog: FoodLog) = foodLogDao.insert(newFoodLog)
    suspend fun delete(deletedFoodLog: FoodLog) = foodLogDao.delete(deletedFoodLog)
    suspend fun getById(id:Int): FoodLog = foodLogDao.getByID(id)
}