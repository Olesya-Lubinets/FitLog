package com.example.fitlog.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.model.WorkoutUI
import com.example.fitlog.data.model.WorkoutUiState
import com.example.fitlog.data.model.DataSource
import com.example.fitlog.data.model.WorkoutFavorite
import com.example.fitlog.data.repository.NoSuchItemException
import com.example.fitlog.data.repository.WorkoutFavoriteRepository
import com.example.fitlog.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val workoutFavoriteRepository: WorkoutFavoriteRepository
) : ViewModel() {

    sealed class ApiResult {
        object EmptyWorkoutApiResult : ApiResult()
        data class Success(val data: List<Workout>) : ApiResult()
        data class FallbackFavorites(val data: List<WorkoutFavorite>) : ApiResult()
        data class Error(val message: String) : ApiResult()
    }

    private val _apiResult = MutableStateFlow<ApiResult>(ApiResult.EmptyWorkoutApiResult)

    val uiState: StateFlow<WorkoutUiState> = combine(
        _apiResult,
        workoutFavoriteRepository.workoutFavoriteFlow
    ) { apiResult, workoutFavoriteFlow ->
        when (apiResult) {
            is ApiResult.Success -> {
                val apiWorkoutList = apiResult.data
                val favoriteNames = workoutFavoriteFlow.map { it.name }.toSet()
                val mergedWorkoutUIList = apiWorkoutList.map { it.toUI(it.name in favoriteNames) }
                WorkoutUiState.SuccessState(mergedWorkoutUIList, DataSource.API)
            }
            is ApiResult.FallbackFavorites -> {
                WorkoutUiState.SuccessState(apiResult.data.map { it.toUI() }, DataSource.FAVORITE)
            }
            is ApiResult.Error -> WorkoutUiState.ErrorSate(apiResult.message)
            ApiResult.EmptyWorkoutApiResult -> WorkoutUiState.Empty
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), WorkoutUiState.Empty
    )

    fun getWorkoutList(activity: String, weight: Int, duration: Int) {
        viewModelScope.launch {
            try {
                _apiResult.value = ApiResult.Success(
                    workoutRepository.getSearchedWorkout(activity, weight, duration))
            } catch (e: NoSuchItemException) {
                _apiResult.value = ApiResult.EmptyWorkoutApiResult
            } catch (e: Exception) {
                val favorites = workoutFavoriteRepository.getAllOnce()
                if (favorites.isEmpty()) {
                    _apiResult.value = ApiResult.Error("No internet and no favorites")
                    Log.d("WorkoutViewModel", "No internet and no favorites")
                } else {
                    Log.d("WorkoutViewModel", "No internet and YES favorites")
                    _apiResult.value = ApiResult.FallbackFavorites(favorites)
                }
            }
        }
    }

    fun toggleFavorite(workoutUI: WorkoutUI) {
        viewModelScope.launch {
            val isFavorite = workoutUI.isFavorite
            if (isFavorite) workoutFavoriteRepository.delete(workoutUI.name)
            else workoutFavoriteRepository.insert(workoutUI.toFavorite())
        }
    }
}