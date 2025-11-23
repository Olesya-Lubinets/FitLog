package com.example.fitlog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.repository.FoodLogRepository
import kotlinx.coroutines.launch

class FoodLogViewModel(private val foodLogRepository: FoodLogRepository):ViewModel() {

    val foodLogList: LiveData<List<FoodLog>> = foodLogRepository.foodLogLiveData

    fun addFoodLog(newFoodLog: FoodLog){
       viewModelScope.launch {
          foodLogRepository.insert(newFoodLog)
        }
    }
    fun deleteFoodLog(deletedFoodLog: FoodLog) {
        viewModelScope.launch {
            foodLogRepository.delete(deletedFoodLog)
        }
    }

    fun getFoodLogById(id: Int, onResult: (FoodLog) -> Unit) {
        viewModelScope.launch {
            val foodLog = foodLogRepository.getById(id)
            onResult(foodLog)
        }
    }
}