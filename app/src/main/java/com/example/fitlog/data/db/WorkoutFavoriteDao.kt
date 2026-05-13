package com.example.fitlog.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.example.fitlog.data.model.WorkoutFavorite

@Dao
interface WorkoutFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutFavorite: WorkoutFavorite)

    @Query("DELETE FROM WorkoutFavorite WHERE name = :name")
    suspend fun delete(name: String)

    @Query("SELECT * FROM  WorkoutFavorite")
    fun getAll(): LiveData<List<WorkoutFavorite>>

    @Query("SELECT * FROM WorkoutFavorite")
    suspend fun getAllOnce(): List<WorkoutFavorite>

    @Query("SELECT EXISTS(SELECT 1 FROM WorkoutFavorite WHERE name = :name)")
    fun isFavorite(name: String):Boolean
}