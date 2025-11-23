package com.example.fitlog.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitlog.data.model.FoodLog

@Dao
interface FoodLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodLog: FoodLog)

    @Delete
    suspend fun delete(foodLog: FoodLog)

    @Query("SELECT * FROM FoodLog")
    fun getAll():LiveData<List<FoodLog>>

    @Query("SELECT * FROM FoodLog WHERE id = :id")
    suspend fun getByID(id:Int): FoodLog
}