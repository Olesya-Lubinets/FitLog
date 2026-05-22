package com.example.fitlog

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.WorkoutUiState
import com.example.fitlog.data.model.convertKGtoPounds
import com.example.fitlog.data.model.DataSource
import com.example.fitlog.ui.WorkoutLogViewModel
import com.example.fitlog.ui.WorkoutViewModel


class AddWorkoutFragment : Fragment() {

    private val workOutViewModel: WorkoutViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private val workoutLogViewModel: WorkoutLogViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchButton = view.findViewById<Button>(R.id.btnSearchWorkout)

        val activity = view.findViewById<EditText>(R.id.etActivity)
        val weight = view.findViewById<EditText>(R.id.etWeight)
        val duration = view.findViewById<EditText>(R.id.etDuration)
        val message = view.findViewById<TextView>(R.id.tvMessage)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvWorkouts)


        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = WorkoutAdapter(
            { workout ->
                workoutLogViewModel.addWorkoutLog(workout.toLog())
                Toast.makeText(context, "Workout added", Toast.LENGTH_SHORT).show()
            }, { workout ->
                workOutViewModel.toggleFavorite(workout)
                Toast.makeText(context, "Workout add/deleted to favorites", Toast.LENGTH_SHORT).show()
            })
        recyclerView.adapter = adapter
        

        searchButton.setOnClickListener {
            val activityString = activity.text.toString()
            val weightInt = weight.text.toString().toInt()
            val durationInt = duration.text.toString().toInt()

            hideKeyboard()

            workOutViewModel.getWorkoutList(
                activityString, convertKGtoPounds(weightInt), durationInt
            )
        }


        workOutViewModel.uiState.observe(viewLifecycleOwner) { state ->
            Log.d("AddWorkout Fragment", "State updated: $state")
            when (state) {
                is WorkoutUiState.SuccessState -> {
                    when (state.source) {
                        DataSource.API -> message.text = "Found:"
                        DataSource.FAVORITE -> message.text =
                            "No internet connection. Favorites shown."
                    }
                    adapter.submitList(state.data)
                }

                WorkoutUiState.Empty -> Toast.makeText(
                    context,
                    "Sorry: nothing found",
                    Toast.LENGTH_SHORT
                ).show()

                is WorkoutUiState.ErrorSate -> Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    private fun hideKeyboard() {
        val imm = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val view = requireActivity().currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        requireView().clearFocus()
    }
}