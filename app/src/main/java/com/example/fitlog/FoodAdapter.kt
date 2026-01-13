package com.example.fitlog

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.Food

class FoodAdapter:RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val foodList= mutableListOf<Food>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newFoodList:List<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.tvFoodName)
        val textDescription: TextView = view.findViewById(R.id.tvCalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.textName.text = foodItem.food_name
        holder.textDescription.text = foodItem.food_description
    }

    override fun getItemCount(): Int = foodList.size
}