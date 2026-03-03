package com.example.fitlog.data.model

import android.annotation.SuppressLint
import java.time.LocalDate
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WorkoutLog (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date:LocalDate,
    val name:String,
    val duration: Int,
    val caloriesBurned:Int
)

@SuppressLint("NewApi")
fun convertWorkoutToWorkoutLog(workout: Workout):WorkoutLog {
    return WorkoutLog(
        date = LocalDate.now(),
        name = workout.name,
        duration = workout.duration_minutes,
        caloriesBurned = workout.total_calories
    )
}