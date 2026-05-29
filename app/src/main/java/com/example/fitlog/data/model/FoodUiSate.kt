package com.example.fitlog.data.model


sealed class FoodUiState {
    class SuccessState(
        val data: List<FoodUI>,
        val source: DataSource
    ) : FoodUiState()

    data class ErrorSate(val message:String) : FoodUiState()
    object Empty:FoodUiState()
}


sealed class FoodDetailsState {
    object Empty: FoodDetailsState()
    data class Success(val data: FoodUI) : FoodDetailsState()
    data class Error(val message: String) : FoodDetailsState()
}
