package com.composeexample.themealappdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.composeexample.themealappdb.model.MealFavorite

@Database(entities = [MealFavorite::class], version = 1, exportSchema = false)
abstract class MealDatabase: RoomDatabase() {

    abstract fun mealDao(): MealDao
}