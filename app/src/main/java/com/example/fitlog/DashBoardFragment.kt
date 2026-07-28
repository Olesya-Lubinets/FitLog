package com.example.fitlog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fitlog.ui.FoodLogViewModel
import com.example.fitlog.ui.WorkoutLogViewModel
import java.time.LocalDate
import com.github.mikephil.charting.charts.LineChart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    private val foodLogViewModel: FoodLogViewModel by viewModels()
    private val workoutLogViewModel: WorkoutLogViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodListED = view.findViewById<TextView>(R.id.etFood)
        val workoutList = view.findViewById<TextView>(R.id.etWorkout)
        val foodChart: LineChart = view.findViewById(R.id.foodChart)
        val workoutChart: LineChart = view.findViewById(R.id.workoutChart)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                foodLogViewModel.foodLogList.collect { foods ->
                    foodListED.text =
                        "Today eaten ${Statistics.calculateTotalFoodForDay(foods, LocalDate.now())}"
                    val dataForChartUpdate = Statistics.calculate5DayFoodStatistic(foods)
                    ChartManager.drawFoodChart(foodChart, dataForChartUpdate)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutLogViewModel.workoutLogFlow.collect { workouts ->
                    workoutList.text = "Today burned ${
                        Statistics.calculateTotalWorkoutForDay(
                            workouts,
                            LocalDate.now()
                        )
                    }"
                    val dataForChartUpdate = Statistics.calculate5DayWorkoutStatistic(workouts)
                    ChartManager.drawWorkoutChart(workoutChart, dataForChartUpdate)
                }
            }
        }
    }
}

