package com.example.fitlog.data.repository

import com.example.fitlog.data.api.FitSecretApiClient
import com.example.fitlog.data.model.WorkoutRequest

class WorkoutRepository {

    val workoutApi = FitSecretApiClient.workoutApi

    suspend fun getSearchedWorkout(searchedWorkout:WorkoutRequest) = workoutApi.findWorkout(searchedWorkout)
}