package com.example.fitlog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitlog.data.repository.FoodLogRepository
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.WorkoutFavoriteRepository
import com.example.fitlog.data.repository.WorkoutLogRepository
import com.example.fitlog.data.repository.WorkoutRepository

class AppViewModelFactory(
    private val foodRepository: FoodRepository,
    private val workoutRepository: WorkoutRepository,
    private val foodLogRepository: FoodLogRepository,
    private val workoutLogRepository: WorkoutLogRepository,
    private val workoutFavoriteRepository: WorkoutFavoriteRepository
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> FoodViewModel(foodRepository) as T
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> WorkoutViewModel(workoutRepository,workoutFavoriteRepository) as T
            modelClass.isAssignableFrom(FoodLogViewModel::class.java) -> FoodLogViewModel(foodLogRepository) as T
            modelClass.isAssignableFrom(WorkoutLogViewModel::class.java) -> WorkoutLogViewModel(workoutLogRepository) as T
            else -> throw IllegalArgumentException()
        }
    }
}