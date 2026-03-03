package com.example.fitlog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.Workout
import com.example.fitlog.data.model.convertKGtoPounds
import com.example.fitlog.data.model.convertWorkoutToWorkoutLog
import com.example.fitlog.ui.FoodViewModel
import com.example.fitlog.ui.WorkoutLogViewModel
import com.example.fitlog.ui.WorkoutViewModel


class AddWorkoutFragment : Fragment() {

    private val workOutViewModel: WorkoutViewModel by viewModels {
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
        return inflater.inflate(R.layout.fragment_add_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedWorkout: Workout? = null

        val searchButton = view.findViewById<Button>(R.id.btnSearchWorkout)

        val activity = view.findViewById<EditText>(R.id.etActivity)
        val weight = view.findViewById<EditText>(R.id.etWeight)
        val duration = view.findViewById<EditText>(R.id.etDuration)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvWorkouts)

        val addToToday = view.findViewById<Button>(R.id.btnAddWorkoutToToday)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = WorkoutAdapter { workout ->
            addToToday.visibility = View.VISIBLE
            selectedWorkout = workout
        }
        recyclerView.adapter = adapter


        searchButton.setOnClickListener {
            val activityString = activity.text.toString()
            val weightInt = weight.text.toString().toInt()
            val durationInt = duration.text.toString().toInt()
            workOutViewModel.getSearchedWorkout(
                activityString,
                convertKGtoPounds(weightInt), durationInt
            )
        }

        workOutViewModel.searchedWorkout.observe(viewLifecycleOwner) { foundWorkout ->
            Log.d("AddWorkout Fragment", "LiveData updated: $foundWorkout")
            adapter.submitList(foundWorkout)
        }

        addToToday.setOnClickListener {
            selectedWorkout?.let { workout ->
                workoutLogViewModel.addWorkoutLog(
                    convertWorkoutToWorkoutLog(
                        workout
                    )
                )
                Toast.makeText(context, "Added to today", Toast.LENGTH_SHORT).show()
            }
            addToToday.visibility = View.GONE
        }
    }
}