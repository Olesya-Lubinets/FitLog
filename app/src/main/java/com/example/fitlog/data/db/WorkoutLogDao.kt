package com.example.fitlog.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitlog.data.model.WorkoutLog

@Dao
interface WorkoutLogDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(workoutLog: WorkoutLog)

        @Delete
        suspend fun delete(workoutLog: WorkoutLog)

        @Query("SELECT * FROM WorkoutLog")
        fun getAll():LiveData<List<WorkoutLog>>

        @Query("SELECT * FROM WorkoutLog WHERE id = :id")
        suspend fun getByID(id:Int): WorkoutLog
}