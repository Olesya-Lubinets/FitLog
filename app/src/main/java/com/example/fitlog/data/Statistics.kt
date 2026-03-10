package com.example.fitlog.data

import android.annotation.SuppressLint
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.model.WorkoutLog
import java.time.LocalDate

@SuppressLint("NewApi")
object Statistics {


    fun getTodayDate():LocalDate =  LocalDate.now()

    fun calculateTotalFoodForDay(foods: List<FoodLog>, date: LocalDate):Int =
        foods.filter { it.date == date }.sumOf { it.calories }

    fun calculateTotalWorkoutForDay(workouts: List<WorkoutLog>, date: LocalDate):Int =
        workouts.filter { it.date == date }.sumOf { it.caloriesBurned }


    fun calculate5DayFoodStatistic(foods: List<FoodLog>): List<Pair<LocalDate, Int>> {
        val last5DaysChronological = (4 downTo 0).map { getTodayDate().minusDays(it.toLong()) }

        return last5DaysChronological.map { date ->
            val calories = calculateTotalFoodForDay(foods, date)
            date to calories
        }
    }


    fun calculate5DayWorkoutStatistic(workouts: List<WorkoutLog>): List<Pair<LocalDate, Int>> {
        val last5DaysChronological = (4 downTo 0).map { getTodayDate().minusDays(it.toLong()) }

        return last5DaysChronological.map { date ->
            val calories = calculateTotalWorkoutForDay(workouts, date)
            date to calories
        }
    }
}