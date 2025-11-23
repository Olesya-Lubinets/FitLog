package com.example.fitlog.data.api


import com.example.fitlog.data.model.WorkoutRequest
import com.example.fitlog.data.model.WorkoutResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkoutApiService {
    @POST("v2/natural/exercise")
    suspend fun findWorkout(@Body workoutRequest: WorkoutRequest): WorkoutResponse
}