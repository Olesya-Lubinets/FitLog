package com.example.fitlog.data.repository

import com.example.fitlog.data.api.workaoutAPI.WorkoutApiService
import com.example.fitlog.data.model.Workout
import javax.inject.Inject

class NoSuchItemException(message:String):Exception(message)

class WorkoutRepository @Inject constructor(private val workoutApi: WorkoutApiService) {

    suspend fun getSearchedWorkout(activity: String, weight: Int, duration: Int): List<Workout> {
            val result :List<Workout> = workoutApi.findWorkout(activity, weight, duration)
            if (result.isEmpty()) throw NoSuchItemException("Nothing found")
            return result
    }
}