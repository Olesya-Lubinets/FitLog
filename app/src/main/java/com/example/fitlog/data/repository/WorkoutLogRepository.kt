package com.example.fitlog.data.repository

import com.example.fitlog.data.db.WorkoutLogDao
import com.example.fitlog.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutLogRepository @Inject constructor(private val workoutLogDao: WorkoutLogDao) {

    val workoutLogList: Flow<List<WorkoutLog>> = workoutLogDao.getAll()

    suspend fun insert(newWorkoutLog: WorkoutLog) = workoutLogDao.insert(newWorkoutLog)
    suspend fun delete(deletedWorkoutLog: WorkoutLog)  = workoutLogDao.delete(deletedWorkoutLog)
    suspend fun getById(idWorkoutLog: Int): WorkoutLog =  workoutLogDao.getByID(idWorkoutLog)
}