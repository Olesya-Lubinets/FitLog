package com.example.fitlog.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.model.WorkoutUI
import com.example.fitlog.data.model.WorkoutUiState
import com.example.fitlog.data.model.DataSource
import com.example.fitlog.data.repository.NoSuchItemException
import com.example.fitlog.data.repository.WorkoutFavoriteRepository
import com.example.fitlog.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository,
    private val workoutFavoriteRepository: WorkoutFavoriteRepository)
    :ViewModel() {

    private val _uiState =  MutableStateFlow<WorkoutUiState>(WorkoutUiState.Empty)
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    fun  getWorkoutList(activity:String, weight:Int, duration:Int) {
        viewModelScope.launch {
            try {
                val workoutList:List<Workout> = workoutRepository.getSearchedWorkout(activity,weight,duration)
                val favoritesNames: List<String> = workoutFavoriteRepository.getAllOnce().map { it.name }
                val uiList:List<WorkoutUI> = workoutList.map { it.toUI(it.name in favoritesNames) }
                _uiState.value = WorkoutUiState.SuccessState(uiList, DataSource.API)

            } catch (e:NoSuchItemException) {
                _uiState.value = WorkoutUiState.Empty

            } catch (e:Exception) {
                val favorites = workoutFavoriteRepository.getAllOnce()
                if (favorites.isEmpty())  {
                    _uiState.value = WorkoutUiState.ErrorSate("No internet and no favorites")
                    Log.d("WorkoutViewModel","No internet and no favorites")
                }
                    else {
                         Log.d("WorkoutViewModel","No internet and YES favorites")
                       _uiState.value = WorkoutUiState.SuccessState(favorites.map { it.toUI()}, DataSource.FAVORITE)
                }
            }
        }
    }

    fun toggleFavorite (workoutUI: WorkoutUI) {
        viewModelScope.launch {
            val isFavorite = workoutUI.isFavorite
            if (isFavorite) workoutFavoriteRepository.delete(workoutUI.name)
            else workoutFavoriteRepository.insert(workoutUI.toFavorite())

            _uiState.update { currentState ->
                if (currentState !is WorkoutUiState.SuccessState ) return@update currentState
                val updateList = currentState.data.map {
                    if (it.name == workoutUI.name) it.copy(isFavorite = !it.isFavorite)
                    else it
                }
                WorkoutUiState.SuccessState(updateList, currentState.source )
            }
        }
    }
}