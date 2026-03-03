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
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.ui.FoodLogViewModel
import com.example.fitlog.ui.FoodViewModel
import java.time.LocalDate
import java.util.Calendar


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
        val foodServings = view.findViewById<TextView>(R.id.tvServings)
        val foodType = view.findViewById<TextView>(R.id.tvFoodType)

        val addToTodayButton = view.findViewById<Button>(R.id.btAddToToday)

        showFoodItemInfo( foodName, foodServings , foodType)

        addToTodayButton.setOnClickListener {
            Toast.makeText(context,"Added to today",Toast.LENGTH_SHORT).show()
            val foodLog = getFoodLogFromFoodX()
            foodLogViewModel.addFoodLog(foodLog)
            Log.d("FoodDetails","Food added to today")
        }
    }

    private fun showFoodItemInfo (foodName:TextView, foodType:TextView, foodServings:TextView) {
        foodViewModel.foodByID.observe(viewLifecycleOwner) {foodByID ->
            foodName.text = foodByID.food.food_name
            foodType.text = foodByID.food.food_type
            val servingsString = foodByID.food.servings.serving.joinToString(separator = "\n\n")
            foodServings.text = servingsString
        }
    }

    @SuppressLint("NewApi")
    fun getFoodLogFromFoodX():FoodLog {
        lateinit var foodLog:FoodLog
        foodViewModel.foodByID.observe(viewLifecycleOwner) { foodByID ->
            foodLog = FoodLog(
                name = foodByID.food.food_name, date = LocalDate.now(),
                    calories = foodByID.food.servings.serving[0].calories.toIntOrNull() ?: 0
            )
        }
        return foodLog
    }
}


