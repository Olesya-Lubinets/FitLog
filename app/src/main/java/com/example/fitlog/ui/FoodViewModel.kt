package com.example.fitlog.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository):ViewModel() {

    private val _foundFood = MutableLiveData<FoodSearchResponse>()
    val foundFood: LiveData<FoodSearchResponse> = _foundFood

    fun getSearchedFood(searchedItem: String) {
        Log.d("FoodViewModel", "Searching for: $searchedItem")
        viewModelScope.launch {
            try {
                val result = foodRepository.getSearchedFood(searchedItem)
                Log.d("FoodViewModel", "Search result: $result")
                _foundFood.value = result
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching food", e)
            }
        }
    }
}