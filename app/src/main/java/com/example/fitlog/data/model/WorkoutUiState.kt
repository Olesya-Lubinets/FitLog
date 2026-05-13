package com.example.fitlog.data.model

enum class dataSource { API, FAVORITE }

sealed class WorkoutUiState {
    class SuccessState(
        val data: List<WorkoutUI>,
        val source: dataSource
    ) : WorkoutUiState()

    data class ErrorSate(val message:String) : WorkoutUiState()
    object Empty:WorkoutUiState()
}

