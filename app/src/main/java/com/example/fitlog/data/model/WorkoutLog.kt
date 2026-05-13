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
    val caloriesBurned:Int
)
