package com.example.fitlog.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodLog
import com.example.fitlog.data.model.WorkoutFavorite
import com.example.fitlog.data.model.WorkoutLog

@Database(entities = [FoodLog::class,WorkoutLog::class, WorkoutFavorite::class, FoodFavorite::class], version = 5)
@TypeConverters(TimeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodLogDao(): FoodLogDao
    abstract fun workoutLogDao(): WorkoutLogDao
    abstract fun workoutFavoriteDao() : WorkoutFavoriteDao
    abstract fun foodFavoriteDao(): FoodFavoriteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitlog_database"
                )
                    //.addMigrations(Migrations.MIGRATION_1_2)
                    //.addMigrations(Migrations.MIGRATION_2_3)
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }
        }
    }
}
