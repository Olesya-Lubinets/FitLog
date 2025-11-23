package com.example.fitlog

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fitlog.data.repository.FoodLogRepository
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.WorkoutLogRepository
import com.example.fitlog.data.repository.WorkoutRepository
import com.example.fitlog.ui.AppViewModelFactory
import com.example.fitlog.ui.FoodViewModel
import com.example.fitlog.ui.WorkoutViewModel

class MainActivity : ComponentActivity() {
    private val appViewModelFactory by lazy {
        AppViewModelFactory(
            foodRepository = FoodRepository(),
            workoutRepository = WorkoutRepository()
           // foodLogRepository = FoodLogRepository(),
            //workoutLogRepository = WorkoutLogRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        val foodViewModel = ViewModelProvider(this, appViewModelFactory)[FoodViewModel::class.java]
        val workoutViewModel = ViewModelProvider(this, appViewModelFactory)[WorkoutViewModel::class.java]


        foodViewModel.foundFood.observe(this) { foundFood ->
            Log.d("MainActivity", "LiveData updated: $foundFood")
            val commonNames = foundFood.common.joinToString("\n") { "- ${it.food_name}" }
            val brandedNames = foundFood.branded.joinToString("\n") { "- ${it.brand_name}: ${it.food_name}" }

            val result = """
        🍎 Common foods:
        $commonNames

        🏷️ Branded foods:
        $brandedNames
    """.trimIndent()

            resultText.text = result
        }

        searchButton.setOnClickListener {
            foodViewModel.getSearchedFood("soup")
        }
    }
}

