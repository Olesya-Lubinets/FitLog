package com.example.fitlog.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL("""
                CREATE TABLE IF NOT EXISTS WorkoutFavorite (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    calories_per_hour INTEGER NOT NULL
                )
            """.trimIndent())

        }
    }
}