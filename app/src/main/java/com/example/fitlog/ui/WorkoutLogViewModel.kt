package com.example.fitlog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.model.WorkoutLog
import com.example.fitlog.data.repository.WorkoutLogRepository
import kotlinx.coroutines.launch

class WorkoutLogViewModel(private val workoutLogRepository: WorkoutLogRepository):ViewModel() {
    val workoutLogLiveData: LiveData<List<WorkoutLog>> = workoutLogRepository.workoutLogLiveData

    fun addWorkoutLog(newWorkoutLog: WorkoutLog) {
        viewModelScope.launch {
            workoutLogRepository.insert(newWorkoutLog)
        }
    }

    fun deleteWorkoutLog(deletedWorkoutLog: WorkoutLog) {
        viewModelScope.launch {
            workoutLogRepository.delete(deletedWorkoutLog)
        }
    }

    fun getWorkoutLogById(id: Int, onResult: (WorkoutLog) -> Unit) {
        viewModelScope.launch {
            val workoutLog = workoutLogRepository.getById(id)
            onResult(workoutLog)
        }
    }
}