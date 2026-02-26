package com.example.fitlog.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlog.R
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.repository.FoodRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository):ViewModel() {

    private val _foundFood = MutableLiveData<FoodSearchResponse>()
    val foundFood: LiveData<FoodSearchResponse> = _foundFood

    private val _foodByID = MutableLiveData<FoodByIDResponse>()
    val foodByID:LiveData<FoodByIDResponse> = _foodByID

    fun getSearchedFood(searchedItem: String) {
        Log.d("FoodViewModel", "Searching for: $searchedItem")
        viewModelScope.launch {
            try {
                val result = foodRepository.getSearchedFood(searchedItem)
                _foundFood.value = result
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching food", e)
            }
        }
    }

    fun getFoodByID(foodID:Long) {
        Log.d("FoodViewModel", "Searching for food with ID: $foodID")
        viewModelScope.launch {
            try {
                val result = foodRepository.getFoodByID(foodID)
                _foodByID.value = result
            } catch (e:Exception){
                Log.e("FoodViewModel", "Error fetching food with ID", e)
            }
        }
    }

    fun loadMockFoodItem(context: Context) {
        val mockResponse = loadMockFoodItemFromFile(context)
        _foodByID.value = mockResponse
    }

    private fun loadMockFoodItemFromFile(context: Context):FoodByIDResponse {
        val current_json = context.resources
            .openRawResource(R.raw.food_item_mock)
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(current_json, FoodByIDResponse::class.java)
    }

    fun loadMockFood(context: Context) {
        val mockResponse = loadFoodSearchResponsefromMock(context)
        _foundFood.value = mockResponse
    }

    private fun loadFoodSearchResponsefromMock(context: Context): FoodSearchResponse {
        val current_json = context.resources
            .openRawResource(R.raw.food_mock)
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(current_json, FoodSearchResponse::class.java)
    }
}