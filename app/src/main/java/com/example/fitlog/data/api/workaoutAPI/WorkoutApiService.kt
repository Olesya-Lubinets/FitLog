package com.example.fitlog.data.api.workaoutAPI

import com.example.fitlog.data.model.Workout
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkoutApiService {
    @GET("v1/caloriesburned")
    suspend fun findWorkout(
        @Query("activity")  activity: String,
        @Query("weigh") weight: Int,
        @Query("duration") duration:Int
    ): List<Workout>
}