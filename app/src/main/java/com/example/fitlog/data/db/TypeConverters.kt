package com.example.fitlog.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class TypeConverters {
        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun fromTimestamp(epochDay: Long?): LocalDate? = epochDay?.let { LocalDate.ofEpochDay(it) }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun dateToTimestamp(date: LocalDate?): Long? =  date?.toEpochDay()
}