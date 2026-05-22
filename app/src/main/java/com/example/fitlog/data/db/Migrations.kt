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
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `FoodFavorite` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `brand_name` TEXT,
                `food_description` TEXT NOT NULL,
                `food_id` INTEGER NOT NULL,
                `food_name` TEXT NOT NULL,
                `food_type` TEXT NOT NULL,
                `food_url` TEXT NOT NULL,
                `servings` TEXT NOT NULL
            )
            """.trimIndent()
            )
        }
    }
}