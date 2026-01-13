package com.example.fitlog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.fitlog.data.repository.FoodRepository
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

        val foodRepository = FoodRepository()
        val workoutRepository = WorkoutRepository()

        viewModelFactory = AppViewModelFactory(
            foodRepository = foodRepository,
            workoutRepository = workoutRepository
        )

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.food_button -> navigateTo(R.id.searchFoodFragment)
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

