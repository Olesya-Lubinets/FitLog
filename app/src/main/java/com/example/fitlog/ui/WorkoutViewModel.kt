package com.example.fitlog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.WorkoutRequest
import com.example.fitlog.data.model.WorkoutResponse
import com.example.fitlog.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(private val workoutRepository: WorkoutRepository):ViewModel() {

    private val _searchedWorkout = MutableLiveData<WorkoutResponse>()
    val searchedWorkout:LiveData<WorkoutResponse> = _searchedWorkout

    fun  getSearchedWorkout(request:WorkoutRequest) {
        viewModelScope.launch {
            try {
                _searchedWorkout.value = workoutRepository.getSearchedWorkout(request)
            }
            catch (e:Exception) {

            }
        }
    }
}