package com.example.fitlog.data.repository

import com.example.fitlog.data.api.NutritionixApiClient
import com.example.fitlog.data.model.WorkoutRequest

class WorkoutRepository {

    val workoutApi = NutritionixApiClient.workoutApi

    suspend fun getSearchedWorkout(searchedWorkout:WorkoutRequest) = workoutApi.findWorkout(searchedWorkout)
}