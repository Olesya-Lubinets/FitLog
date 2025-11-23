package com.example.fitlog.data.repository

import androidx.lifecycle.LiveData
import com.example.fitlog.data.db.WorkoutLogDao
import com.example.fitlog.data.model.WorkoutLog

class WorkoutLogRepository(private val workoutLogDao: WorkoutLogDao) {
    val workoutLogLiveData: LiveData<List<WorkoutLog>> = workoutLogDao.getAll()

    suspend fun insert(newWorkoutLog: WorkoutLog) = workoutLogDao.insert(newWorkoutLog)
    suspend fun delete(deletedWorkoutLog: WorkoutLog)  = workoutLogDao.delete(deletedWorkoutLog)
    suspend fun getById(idWorkoutLog: Int): WorkoutLog =  workoutLogDao.getByID(idWorkoutLog)
}