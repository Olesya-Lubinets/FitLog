package com.example.fitlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.FoodDetailsState
import com.example.fitlog.data.model.FoodUI
import com.example.fitlog.ui.FoodLogViewModel
import com.example.fitlog.ui.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodDetailsFragment : Fragment() {

    private val foodViewModel: FoodViewModel by viewModels()
    private val foodLogViewModel:FoodLogViewModel by viewModels()

    private val args: FoodDetailsFragmentArgs by navArgs()
    private var foodID:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodID = args.foodId
        foodViewModel.getFoodByID(foodID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodName = view.findViewById<TextView>(R.id.tvFoodName)
        val foodType = view.findViewById<TextView>(R.id.tvFoodType)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvServings)

        var selectedFood:FoodUI? = null

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ServingAdapter { serving ->
            if (selectedFood!=null) {
                val selectedLog = selectedFood!!.toLog(serving.calories.toIntOrNull() ?: 0)
                foodLogViewModel.addFoodLog(selectedLog)
            }
            Toast.makeText(context,"Food added",Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                foodViewModel.foodByIDUIState.collect { state ->
                    when(state) {
                        is FoodDetailsState.Success -> {
                            val foodByID= state.data
                            foodName.text = foodByID.food_name
                            foodType.text = foodByID.food_type
                            adapter.submitList(foodByID.servings?.serving ?: emptyList())
                            selectedFood = foodByID
                        }
                        is FoodDetailsState.Empty -> Toast.makeText(
                            context,
                            "Sorry: nothing found",
                            Toast.LENGTH_SHORT
                        ).show()
                        is FoodDetailsState.Error -> Toast.makeText(
                            context,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}


