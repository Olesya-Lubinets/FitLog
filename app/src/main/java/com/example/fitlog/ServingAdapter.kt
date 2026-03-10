package com.example.fitlog

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.Serving
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button

class ServingAdapter(private val onItemClicked:(Serving) -> Unit)
    : RecyclerView.Adapter<ServingAdapter.ServingViewHolder>() {

    private val servingList = mutableListOf<Serving>()
    private var selectedPosition = -1

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newServingList:List<Serving>) {
        servingList.clear()
        servingList.addAll(newServingList)
        notifyDataSetChanged()
    }

    class ServingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvServingDescription: TextView = view.findViewById(R.id.tvServingDescription)
        val tvCalories: TextView = view.findViewById(R.id.tvCalories)
        val tvProtein:TextView = view.findViewById(R.id.tvProtein)
        val tvFat:TextView = view.findViewById(R.id.tvFat)
        val tvCarbs:TextView = view.findViewById(R.id.tvCarbs)
        val tvSugar:TextView = view.findViewById(R.id.tvSugar)
        val tvFiber:TextView = view.findViewById(R.id.tvFiber)
        val addToToday: Button= view.findViewById(R.id.btnAddFoodToToday)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.serving_item, parent, false)
        return ServingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServingViewHolder, position: Int) {
        val servingItem = servingList[position]
        holder.tvServingDescription.text = servingItem.serving_description
        holder.tvCalories.text = servingItem.calories
        holder.tvProtein.text = "${servingItem.protein} g"
        holder.tvFat.text = "${servingItem.fat} g"
        holder.tvCarbs.text = "${servingItem.carbohydrate} g"
        holder.tvSugar.text = "${servingItem.sugar} g"
        holder.tvFiber.text = "${servingItem.fiber} g"


        holder.addToToday.setOnClickListener {
            onItemClicked(servingItem)
            holder.addToToday.visibility = View.GONE
        }

        holder.itemView.setOnClickListener{
            val previous = selectedPosition
            selectedPosition = holder.bindingAdapterPosition

            if (previous != -1) notifyItemChanged(previous)
            notifyItemChanged(selectedPosition)
        }

        holder.addToToday.visibility =  if (holder.bindingAdapterPosition == selectedPosition)
            View.VISIBLE else View.GONE
    }
    override fun getItemCount(): Int = servingList.size
}