package com.example.fitlog


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import com.example.fitlog.data.model.Workout

class WorkoutAdapter(private val onItemClicked:(Workout) -> Unit)
    :RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val workoutList = mutableListOf<Workout>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newWorkoutList:List<Workout>) {
        workoutList.clear()
        workoutList.addAll(newWorkoutList)
        notifyDataSetChanged()
    }

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textWorkoutName: TextView = view.findViewById(R.id.tvWorkoutName)
        val textCaloriesPerHour: TextView = view.findViewById(R.id.tvCaloriesPerHour)
        val textDuration:TextView = view.findViewById(R.id.tvDuration)
        val textTotalCalories:TextView = view.findViewById(R.id.tvTotalCalories)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_item, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workoutItem = workoutList[position]
        holder.textWorkoutName.text = workoutItem.name
        holder.textCaloriesPerHour.text = workoutItem.calories_per_hour.toString()
        holder.textDuration.text = workoutItem.duration_minutes.toString()
        holder.textTotalCalories.text = workoutItem.total_calories.toString()


        holder.itemView.setOnClickListener {
            onItemClicked(workoutItem)
        }
    }
    override fun getItemCount(): Int = workoutList.size
}