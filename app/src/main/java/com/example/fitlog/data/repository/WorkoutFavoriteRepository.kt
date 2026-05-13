package com.example.fitlog.data.repository

import androidx.lifecycle.LiveData
import com.example.fitlog.data.api.workaoutAPI.WorkoutRetrofit
import com.example.fitlog.data.db.WorkoutFavoriteDao
import com.example.fitlog.data.model.WorkoutFavorite
import com.example.fitlog.data.model.WorkoutLog

class WorkoutFavoriteRepository(private val workoutFavoriteDao: WorkoutFavoriteDao) {
    val workoutFavoriteLiveData: LiveData<List<WorkoutFavorite>> = workoutFavoriteDao.getAll()

    suspend fun getAll() = workoutFavoriteDao.getAll()
    suspend fun insert(newWorkoutLog: WorkoutFavorite) = workoutFavoriteDao.insert(newWorkoutLog)
    suspend fun delete(name: String)  = workoutFavoriteDao.delete(name)
    suspend fun isFavorite(name: String) = workoutFavoriteDao.isFavorite(name)
    suspend fun getAllOnce():List<WorkoutFavorite> = workoutFavoriteDao.getAllOnce()
}