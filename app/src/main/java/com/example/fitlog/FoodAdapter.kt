package com.example.fitlog

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.FoodUI
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter



class FoodAdapter(
    private val onFavoriteClicked:(FoodUI)->Unit,
    private val onAddToTodayClicked:(FoodUI)->Unit)
    :ListAdapter<FoodUI, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    var selectedPosition = -1

        class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.tvFoodName)
        val textDescription: TextView = view.findViewById(R.id.tvCalories)
        val favoriteButton: ImageButton = view.findViewById(R.id.btnAddFoodToFavorite)
        val addToTodayButton: Button = view.findViewById(R.id.btnAddFoodToTodayFoodItems)
        val buttonFoodContainer: LinearLayout = view.findViewById(R.id.buttonFoodContainer)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = getItem(position)
        holder.textName.text = foodItem.food_name
        holder.textDescription.text = foodItem.food_description
        holder.favoriteButton.isSelected = foodItem.isFavorite



        holder.buttonFoodContainer.visibility = if (holder.bindingAdapterPosition == selectedPosition) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition

            if (previousPosition!=-1) notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }

        holder.favoriteButton.setOnClickListener {
            val currentItem = getItem(holder.bindingAdapterPosition)
            onFavoriteClicked(currentItem)
        }

        holder.addToTodayButton.setOnClickListener {
            val currentItem = getItem(holder.bindingAdapterPosition)
            onAddToTodayClicked(currentItem)
        }
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int, payloads :MutableList<Any> ) {
        if (payloads.contains("PAYLOAD_FAVORITE"))
        {
            val item = getItem(position)
            holder.favoriteButton.isSelected = item.isFavorite
        } else {
            super.onBindViewHolder(holder, position, payloads) }
    }
}

class FoodDiffCallback: DiffUtil.ItemCallback<FoodUI>() {
    override fun areItemsTheSame(oldItem: FoodUI, newItem: FoodUI): Boolean {
        return oldItem.food_id == newItem.food_id
    }

    override fun areContentsTheSame(oldItem: FoodUI, newItem: FoodUI): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: FoodUI, newItem: FoodUI): Any? {
        return if (oldItem.isFavorite != newItem.isFavorite) {
            "PAYLOAD_FAVORITE"
        } else null
    }
}
