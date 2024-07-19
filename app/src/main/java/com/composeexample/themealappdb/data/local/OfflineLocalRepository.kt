package com.composeexample.themealappdb.data.local

import com.composeexample.themealappdb.model.MealFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class OfflineLocalRepository @Inject constructor(private val mealDao: MealDao): LocalRepository {

    override fun getAllMealsStream(): Flow<List<MealFavorite>> = mealDao.getAllMeals()

    override suspend fun insertMeal(mealFavorite: MealFavorite) {
        mealDao.insert(mealFavorite)
    }

    override suspend fun deleteMeal(mealFavorite: MealFavorite) {
        mealDao.delete(mealFavorite)
    }

    override suspend fun isMealFavorite(mealFavorite: MealFavorite): Boolean = mealDao.getMealById(mealFavorite.idMeal) != null
}