package com.example.fitlog.data.model

enum class DataSource { API, FAVORITE }

sealed class WorkoutUiState {
    class SuccessState(
        val data: List<WorkoutUI>,
        val source: DataSource
    ) : WorkoutUiState()

    data class ErrorSate(val message:String) : WorkoutUiState()
    object Empty:WorkoutUiState()
}

