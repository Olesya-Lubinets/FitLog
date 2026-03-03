package com.example.fitlog.data.repository

import com.example.fitlog.data.api.workaoutAPI.WorkoutRetrofit


class WorkoutRepository {

    private val workoutApi = WorkoutRetrofit.workoutAPI

    suspend fun getSearchedWorkout( activity:String,weight:Int,duration:Int) =
        workoutApi.findWorkout(activity,weight,duration)
}