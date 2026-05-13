package com.example.fitlog.data.repository

import com.example.fitlog.data.api.workaoutAPI.WorkoutRetrofit
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.model.WorkoutUiState

class NoSuchItemException(message:String):Exception(message)

class WorkoutRepository {

    private val workoutApi = WorkoutRetrofit.workoutAPI

    suspend fun getSearchedWorkout(activity: String, weight: Int, duration: Int): List<Workout> {
            val result :List<Workout> = workoutApi.findWorkout(activity, weight, duration)
            if (result.isEmpty()) throw NoSuchItemException("Nothing found")
            return result
    }
}