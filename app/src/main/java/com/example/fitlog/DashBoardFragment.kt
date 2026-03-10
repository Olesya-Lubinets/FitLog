package com.example.fitlog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.fitlog.data.Statistics
import com.example.fitlog.ui.FoodLogViewModel
import com.example.fitlog.ui.WorkoutLogViewModel
import java.time.LocalDate

class DashBoardFragment : Fragment() {

    private val foodLogViewModel: FoodLogViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private val workoutLogViewModel: WorkoutLogViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodListED  = view.findViewById<TextView>(R.id.etFood)
        val workoutList = view.findViewById<TextView>(R.id.etWorkout)

        foodLogViewModel.foodLogList.observe(viewLifecycleOwner) { foods ->
            foodListED.text =
                    Statistics.calculate5DayFoodStatistic(foods).map {"${it.first}:${it.second} "}
                        .joinToString (separator = "\n")
        }
        workoutLogViewModel.workoutLogList.observe(viewLifecycleOwner) { workouts ->
            workoutList.text =Statistics.calculateTotalWorkoutForDay(workouts,LocalDate.now()).toString() +
                    Statistics.calculate5DayWorkoutStatistic(workouts).joinToString  (separator = " ")
        }

    }
}