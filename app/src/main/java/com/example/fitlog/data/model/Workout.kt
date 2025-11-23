package com.example.fitlog.data.model

data class Workout(
    val benefits: String?,
    val compendium_code: Int,
    val description: String?,
    val duration_min: Int,
    val met: Double,
    val name: String,
    val nf_calories: Double,
    val photo: Photo?,
    val tag_id: Int,
    val user_input: String
)

data class WorkoutRequest(
    val request: String,
    val gender: String,
    val weight_kg: Double,
    val height_cm: Double,
    val age: Int
)

data class WorkoutResponse(
    val exercises:List<Workout>
)