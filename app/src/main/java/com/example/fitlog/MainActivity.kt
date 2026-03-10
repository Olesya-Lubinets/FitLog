package com.example.fitlog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.fitlog.data.db.AppDatabase
import com.example.fitlog.data.repository.FoodLogRepository
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.WorkoutLogRepository
import com.example.fitlog.data.repository.WorkoutRepository
import com.example.fitlog.databinding.ActivityMainBinding
import com.example.fitlog.ui.AppViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModelFactory: AppViewModelFactory


    val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        val foodLogDao = db.foodLogDao()
        val workOutLogDao = db.workoutLogDao()

        val foodRepository = FoodRepository()
        val workoutRepository = WorkoutRepository()
        val foodLogRepository = FoodLogRepository(foodLogDao)
        val workoutLogRepository = WorkoutLogRepository(workOutLogDao)

        viewModelFactory = AppViewModelFactory(
            foodRepository = foodRepository,
            workoutRepository = workoutRepository,
            foodLogRepository = foodLogRepository,
            workoutLogRepository = workoutLogRepository
        )

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.food_button -> navigateTo(R.id.searchFoodFragment)
                R.id.workout_button -> navigateTo(R.id.addWorkoutFragment)
                R.id.dashboard_button -> navigateTo(R.id.dashBoardFragment)
            }
            true
        }
    }

    private fun navigateTo(destinationId: Int) {
        if (navController.currentDestination?.id != destinationId) {
            navController.navigate(destinationId)
        }
    }
}

