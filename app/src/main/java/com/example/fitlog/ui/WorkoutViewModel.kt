package com.example.fitlog.ui

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.model.WorkoutUI
import com.example.fitlog.data.model.WorkoutUiState
import com.example.fitlog.data.model.dataSource
import com.example.fitlog.data.repository.NoSuchItemException
import com.example.fitlog.data.repository.WorkoutFavoriteRepository
import com.example.fitlog.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository,
    private val workoutFavoriteRepository: WorkoutFavoriteRepository)
    :ViewModel() {

    private val _uiState = MutableLiveData<WorkoutUiState>()
    val uiState:LiveData<WorkoutUiState> = _uiState

    fun  getWorkoutList(activity:String, weight:Int, duration:Int) {
        viewModelScope.launch {
            try {
                val workoutList:List<Workout> = workoutRepository.getSearchedWorkout(activity,weight,duration)
                val favoritesNames: List<String> = workoutFavoriteRepository.getAllOnce().map { it.name }
                val uiList:List<WorkoutUI> = workoutList.map { it.toUI(it.name in favoritesNames) }
                _uiState.value = WorkoutUiState.SuccessState(uiList, dataSource.API)

            } catch (e:NoSuchItemException) {
                _uiState.value = WorkoutUiState.Empty

            } catch (e:Exception) {
                val favorites = workoutFavoriteRepository.getAllOnce()
                if (favorites.isEmpty())  _uiState.value = WorkoutUiState.ErrorSate("No internet and no favorites")
                    else {
                       _uiState.value = WorkoutUiState.SuccessState(favorites.map { it.toUI()}, dataSource.FAVORITE)
                }
            }
        }
    }

    fun toggleFavorite (workoutUI: WorkoutUI) {
        viewModelScope.launch {
            val isFavorite = workoutUI.isFavorite
            if (isFavorite) workoutFavoriteRepository.delete(workoutUI.name)
            else workoutFavoriteRepository.insert(workoutUI.toFavorite())

            val currentState = uiState.value as WorkoutUiState.SuccessState
            val updateList = currentState.data.map {
                if (it.name == workoutUI.name) it.copy(isFavorite = !it.isFavorite)
                else it
            }
            _uiState.value =  WorkoutUiState.SuccessState(updateList, currentState.source )
        }
    }
}