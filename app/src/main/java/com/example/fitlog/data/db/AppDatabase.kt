package com.example.fitlog.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.model.WorkoutLog

@Database(entities = [FoodLog::class,WorkoutLog::class], version = 1)
@TypeConverters(TimeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodLogDao(): FoodLogDao
    abstract fun workoutLogDao(): WorkoutLogDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitlog_database"
                ).build().also { INSTANCE = it }

            }
        }
    }
}
