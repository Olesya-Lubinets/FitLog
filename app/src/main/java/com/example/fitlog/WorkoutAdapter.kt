package com.example.fitlog


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.fitlog.data.model.WorkoutUI

class WorkoutAdapter(
    private val onAddClicked: (WorkoutUI) -> Unit,
    private val onFavoriteClicked: (WorkoutUI) -> Unit,
) : ListAdapter<WorkoutUI, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallBack()) {


    private var selectedPosition = -1

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textWorkoutName: TextView = view.findViewById(R.id.tvWorkoutName)
        val textCaloriesPerHour: TextView = view.findViewById(R.id.tvCaloriesPerHour)
        val textDuration: TextView = view.findViewById(R.id.tvDuration)
        val textTotalCalories: TextView = view.findViewById(R.id.tvTotalCalories)
        val addToToday: Button = view.findViewById(R.id.btnAddWorkoutToToday)
        val buttonContainer: LinearLayout = view.findViewById(R.id.buttonContainer)
        val btnAddWorkoutToFavorite: ImageButton = view.findViewById(R.id.btnAddWorkoutToFavorite)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_item, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workoutItem = getItem(position)
        holder.textWorkoutName.text = workoutItem.name
        holder.textDuration.text = workoutItem.duration_minutes.toString()
        holder.textCaloriesPerHour.text = workoutItem.calories_per_hour.toString()
        holder.textTotalCalories.text = workoutItem.total_calories.toString()


        holder.btnAddWorkoutToFavorite.isSelected = workoutItem.isFavorite

        holder.buttonContainer.visibility =
            if (holder.bindingAdapterPosition == selectedPosition) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val previous = selectedPosition
            selectedPosition = holder.bindingAdapterPosition

            if (previous != -1) notifyItemChanged(previous)
            notifyItemChanged(selectedPosition)
        }

        holder.addToToday.setOnClickListener {
            val currentItem = getItem(holder.bindingAdapterPosition)
            onAddClicked(currentItem)
        }

        holder.btnAddWorkoutToFavorite.setOnClickListener {
            val currentItem = getItem(holder.bindingAdapterPosition)
            onFavoriteClicked(currentItem)
        }
    }

    override fun onBindViewHolder(
        holder: WorkoutViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains("PAYLOAD_FAVORITE")) {
            val workoutItem = getItem(position)
            holder.btnAddWorkoutToFavorite.isSelected = workoutItem.isFavorite
        }
     else  super.onBindViewHolder(holder, position, payloads)
    }
}



class WorkoutDiffCallBack : DiffUtil.ItemCallback<WorkoutUI>() {
    override fun areItemsTheSame(oldItem: WorkoutUI, newItem: WorkoutUI): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: WorkoutUI, newItem: WorkoutUI): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: WorkoutUI, newItem: WorkoutUI): Any? {
        Log.e("WorkoutAdapter", "Compare old = ${oldItem.isFavorite} and new = ${newItem.isFavorite}")
        return if (oldItem.isFavorite != newItem.isFavorite) "PAYLOAD_FAVORITE"
        else null
    }
}
