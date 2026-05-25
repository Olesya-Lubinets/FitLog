package com.example.fitlog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.WorkoutLog
import com.example.fitlog.data.repository.WorkoutLogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutLogViewModel(private val workoutLogRepository: WorkoutLogRepository):ViewModel() {

    val workoutLogFlow: StateFlow<List<WorkoutLog>> = workoutLogRepository.workoutLogList.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

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