package com.example.fitlog.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.fitlog.data.model.Servings
import com.google.gson.Gson
import java.time.LocalDate

class TimeTypeConverters {
        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun fromTimestamp(epochDay: Long?): LocalDate? = epochDay?.let { LocalDate.ofEpochDay(it) }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun dateToTimestamp(date: LocalDate?): Long? =  date?.toEpochDay()

        private val gson = Gson()

        @TypeConverter
        fun fromServings(servings: Servings): String {
                return gson.toJson(servings)
        }

        @TypeConverter
        fun toServings(data: String): Servings {
                return gson.fromJson(data, Servings::class.java)
        }
}