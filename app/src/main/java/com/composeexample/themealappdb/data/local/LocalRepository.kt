package com.composeexample.themealappdb.data.local

import com.composeexample.themealappdb.model.MealFavorite
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllMealsStream(): Flow<List<MealFavorite>>
    suspend fun insertMeal(mealFavorite: MealFavorite)
    suspend fun deleteMeal(mealFavorite: MealFavorite)
    suspend fun isMealFavorite(mealFavorite: MealFavorite): Boolean
}