package com.example.fitlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fitlog.ui.FoodViewModel


class FoodDetailsFragment : Fragment() {
    private val foodViewModel: FoodViewModel by viewModels {
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

        val addToFavoriteButton = view.findViewById<Button>(R.id.btAddToFavorite)

        foodViewModel.foodByID.observe(viewLifecycleOwner) {foodByID ->
            foodName.text = foodByID.food.food_name
            foodType.text = foodByID.food.food_type
            val servingsString = foodByID.food.servings.serving.joinToString(separator = "\n\n")
            foodServings.text = servingsString
        }

        addToFavoriteButton.setOnClickListener {
            Toast.makeText(context,"Added to favorite",Toast.LENGTH_SHORT).show()
        }
    }
}
