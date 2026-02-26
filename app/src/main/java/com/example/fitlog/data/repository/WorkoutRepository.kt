package com.example.fitlog.data.repository

import com.example.fitlog.data.api.FatSecretAPIClient
import com.example.fitlog.data.model.WorkoutRequest

class WorkoutRepository {

    val workoutApi = FatSecretAPIClient.workoutApi

    suspend fun getSearchedWorkout(searchedWorkout:WorkoutRequest) = workoutApi.findWorkout(searchedWorkout)
}