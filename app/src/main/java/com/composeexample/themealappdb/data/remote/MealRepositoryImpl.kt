package com.composeexample.themealappdb.data.remote

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class MealRepositoryImpl @Inject constructor(
    private val webService: WebService
): MealRepository {

    override suspend fun getMealsByName(mealName: String) = webService.getMealByName(mealName)
    override suspend fun getFullMealDetailsById(idMeal: Int) = webService.getFullMealDetailsById(idMeal)
}