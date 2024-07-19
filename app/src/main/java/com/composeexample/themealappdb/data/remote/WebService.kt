package com.composeexample.themealappdb.data.remote

import com.composeexample.themealappdb.model.MealList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("search.php")
    suspend fun getMealByName(@Query("s") mealName: String): MealList

    @GET("lookup.php")
    suspend fun getFullMealDetailsById(@Query("i") idMeal: Int): MealList
}