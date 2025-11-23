package com.example.fitlog.data.model

import androidx.room.PrimaryKey
import java.time.LocalDate
import androidx.room.Entity

@Entity
data class FoodLog (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date:LocalDate,
    val name:String,
    val calories:Int
)