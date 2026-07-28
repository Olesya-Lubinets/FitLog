package com.example.fitlog.di

import android.content.Context
import androidx.room.Room
import com.example.fitlog.data.db.AppDatabase
import com.example.fitlog.data.db.FoodFavoriteDao
import com.example.fitlog.data.db.FoodLogDao
import com.example.fitlog.data.db.WorkoutFavoriteDao
import com.example.fitlog.data.db.WorkoutLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext ctx: Context): AppDatabase {
        return Room.databaseBuilder(
            ctx.applicationContext,
            AppDatabase::class.java,
            "fitlog_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun providesFavoriteFoodDao(db: AppDatabase): FoodFavoriteDao = db.foodFavoriteDao()

    @Provides
    fun providesFoodLogDao(db: AppDatabase): FoodLogDao = db.foodLogDao()

    @Provides
    fun providesWorkoutFavoriteDao(db: AppDatabase): WorkoutFavoriteDao = db.workoutFavoriteDao()

    @Provides
    fun providesWorkoutLogDao(db: AppDatabase): WorkoutLogDao = db.workoutLogDao()
}