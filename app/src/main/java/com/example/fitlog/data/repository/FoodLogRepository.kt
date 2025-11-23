package com.example.fitlog.data.repository

import androidx.lifecycle.LiveData
import com.example.fitlog.data.db.FoodLogDao
import com.example.fitlog.data.model.FoodLog

class FoodLogRepository(private val foodLogDao: FoodLogDao) {

    val foodLogLiveData: LiveData<List<FoodLog>>  = foodLogDao.getAll()

    suspend fun insert(newFoodLog: FoodLog) = foodLogDao.insert(newFoodLog)
    suspend fun delete(deletedFoodLog: FoodLog) = foodLogDao.delete(deletedFoodLog)
    suspend fun getById(id:Int): FoodLog = foodLogDao.getByID(id)
}