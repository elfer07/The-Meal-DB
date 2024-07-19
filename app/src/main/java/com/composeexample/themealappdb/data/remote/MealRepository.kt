package com.composeexample.themealappdb.data.remote

import com.composeexample.themealappdb.model.MealList

interface MealRepository {
    suspend fun getMealsByName(mealName: String): MealList
    suspend fun getFullMealDetailsById(idMeal: Int): MealList
}