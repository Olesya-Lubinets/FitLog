package com.example.fitlog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(private val workoutRepository: WorkoutRepository):ViewModel() {

    private val _searchedWorkout = MutableLiveData<List<Workout>>()
    val searchedWorkout:LiveData<List<Workout>> = _searchedWorkout

    fun  getSearchedWorkout(activity:String,weight:Int,duration:Int) {
        viewModelScope.launch {
            try {
                _searchedWorkout.value = workoutRepository.getSearchedWorkout(activity,weight,duration)
            }
            catch (e:Exception) {

            }
        }
    }
}