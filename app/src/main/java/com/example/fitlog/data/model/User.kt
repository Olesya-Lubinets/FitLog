package com.example.fitlog.data.model

import androidx.compose.ui.Modifier
import kotlin.math.roundToInt

data class User(
    val gender: String,
    val weight_kg: Int,
    val height_cm: Int,
    val age: Int
)



fun convertKGtoPounds(weight_kg: Int):Int = (weight_kg*2.20462).roundToInt()


