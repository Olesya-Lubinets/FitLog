package com.example.fitlog.data.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


data class Workout(
    val name:String,
    val calories_per_hour: Int,
    val duration_minutes: Int,
    val total_calories:Int
) {
    fun toUI(isFavorite:Boolean):WorkoutUI
    {
       return  WorkoutUI(name = name, calories_per_hour = calories_per_hour,
        duration_minutes = duration_minutes, total_calories = total_calories, isFavorite= isFavorite)
    }
}


@Entity
data class WorkoutFavorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String,
    val calories_per_hour: Int,
    val duration_minutes: Int,
    val total_calories: Int
) {
    fun toUI():WorkoutUI = WorkoutUI(name = name, calories_per_hour = calories_per_hour,
        duration_minutes = duration_minutes, total_calories = total_calories, true)
}

data class WorkoutUI(
    val name: String,
    val calories_per_hour: Int,
    val duration_minutes: Int,
    val total_calories: Int,
    val isFavorite: Boolean
) {
    @SuppressLint("NewApi")
    fun toLog() = WorkoutLog(
        date = LocalDate.now(),
        name = name,
        caloriesBurned = total_calories
    )
    fun toFavorite() = WorkoutFavorite(name = name, calories_per_hour = calories_per_hour,
        duration_minutes = duration_minutes, total_calories = total_calories)
}