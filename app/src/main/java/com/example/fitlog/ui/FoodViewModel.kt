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
import com.example.fitlog.data.model.FoodUI
import com.example.fitlog.data.model.FoodUiState
import com.example.fitlog.data.model.DataSource
import com.example.fitlog.data.model.FoodX
import com.example.fitlog.data.repository.FoodFavoriteRepository
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.NoSuchItemException
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodViewModel(
    private val foodRepository: FoodRepository,
    private val foodFavoriteRepository: FoodFavoriteRepository
) : ViewModel() {

    private val _foundFood = MutableLiveData<FoodSearchResponse>()
    val foundFood: LiveData<FoodSearchResponse> = _foundFood

    private val _foodUIState = MutableStateFlow<FoodUiState>(FoodUiState.Empty)
    val foodUiState: StateFlow<FoodUiState> = _foodUIState.asStateFlow()

    private val _foodByID = MutableLiveData<FoodUI>()
    val foodByID: LiveData<FoodUI> = _foodByID

    fun getSearchedFood(searchedItem: String) {
        Log.d("FoodViewModel", "Searching for: $searchedItem")
        viewModelScope.launch {
            try {
                val listOfFood = foodRepository.getSearchedFood(searchedItem).foods.food
                val listOfFavoritesIds = foodFavoriteRepository.getAllOnce().map { it.food_id }
                val listFoodUi = listOfFood.map { it.toUi(it.food_id in listOfFavoritesIds) }
                _foodUIState.value = FoodUiState.SuccessState(listFoodUi, DataSource.API)
            } catch (e: NoSuchItemException) {
                _foodUIState.value = FoodUiState.Empty
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching food", e)
                val listOfFavorites = foodFavoriteRepository.getAllOnce()
                if (listOfFavorites.isEmpty()) _foodUIState.value =
                    FoodUiState.ErrorSate("No response from API and empty favorites list")
                else {
                    _foodUIState.value = FoodUiState.SuccessState(
                        listOfFavorites.map { it.toUi() },
                        DataSource.FAVORITE
                    )
                }
            }
        }
    }

    fun getFoodByID(foodID: Long) {
        Log.d("FoodViewModel", "Searching for food with ID: $foodID")
        viewModelScope.launch {
            try {
                val result = foodRepository.getFoodByID(foodID).food.toUI()
                _foodByID.value = result
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching food with ID from API", e)
                try {
                    val result = foodFavoriteRepository.getById(foodID)
                    if (result == null) Log.e(
                        "FoodViewModel",
                        "Error fetching food with ID and it doesn't exist in favorites",
                        e
                    )
                    else {
                        _foodByID.value = result.toUi()
                        Log.e("FoodViewModel", "Got data from favorites")
                    }
                } catch (e: Exception) {
                    Log.e(
                        "FoodViewModel",
                        "Error fetching food with ID and it doesn't exist in favorites",
                        e
                    )
                }
            }
        }
    }


    fun toggleFood(foodUI: FoodUI) {
        viewModelScope.launch {
            if (foodUI.isFavorite) foodFavoriteRepository.delete(foodUI.food_name)
            else {
                val servings = foodRepository.getFoodServingsByID(foodUI.food_id)
                foodFavoriteRepository.insert(foodUI.toFavorite(servings))
            }
            _foodUIState.update { currentState ->
                if (currentState !is FoodUiState.SuccessState) return@update currentState
                val newList = currentState.data.map {
                    if (it.food_id == foodUI.food_id) it.copy(isFavorite = !it.isFavorite)
                    else it
                }
                FoodUiState.SuccessState(newList, currentState.source)
            }
        }
    }


    fun loadMockFoodItem(context: Context) {
        val mockResponse = loadMockFoodItemFromFile(context)
        _foodByID.value = mockResponse
    }

    private fun loadMockFoodItemFromFile(context: Context): FoodUI {
        val current_json = context.resources
            .openRawResource(R.raw.food_item_mock)
            .bufferedReader()
            .use { it.readText() }
        val foodByIDResponse = Gson().fromJson(current_json, FoodByIDResponse::class.java)
        return foodByIDResponse.food.toUI()
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