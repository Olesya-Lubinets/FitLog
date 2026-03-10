package com.example.fitlog

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.model.FoodX
import com.example.fitlog.data.model.Serving
import com.example.fitlog.ui.FoodLogViewModel
import com.example.fitlog.ui.FoodViewModel
import java.time.LocalDate



class FoodDetailsFragment : Fragment() {
    private val foodViewModel: FoodViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private val foodLogViewModel:FoodLogViewModel by viewModels {
                 (requireActivity() as MainActivity).viewModelFactory
    }

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


        var selectedFood:FoodX? = null

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ServingAdapter { serving ->
            if (selectedFood!=null) {
                foodLogViewModel.addFoodLog(
                    getFoodLogFromFoodX(selectedFood!!, serving)
                )
            }
            Toast.makeText(context,"Food added",Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        foodViewModel.foodByID.observe(viewLifecycleOwner) {foodByID ->
            foodName.text = foodByID.food.food_name
            foodType.text = foodByID.food.food_type
            adapter.submitList(foodByID.food.servings.serving)
            selectedFood = foodByID.food
        }

    }


    @SuppressLint("NewApi")
    fun getFoodLogFromFoodX(food: FoodX,serving:Serving):FoodLog {
        lateinit var foodLog:FoodLog
        foodViewModel.foodByID.observe(viewLifecycleOwner) { foodByID ->
            foodLog = FoodLog(
                name = food.food_name, date = LocalDate.now(),
                    calories = serving.calories.toIntOrNull()?:0
            )
        }
        return foodLog
    }
}


