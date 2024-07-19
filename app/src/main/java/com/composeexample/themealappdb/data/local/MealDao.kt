package com.composeexample.themealappdb.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.composeexample.themealappdb.model.MealFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: MealFavorite)

    @Update
    suspend fun update(meal: MealFavorite)

    @Delete
    suspend fun delete(meal: MealFavorite)

    @Query("SELECT * FROM favorites WHERE idMeal = :mealId")
    suspend fun getMealById(mealId: Int): MealFavorite

    @Query("SELECT * from favorites ORDER BY idMeal ASC")
    fun getAllMeals(): Flow<List<MealFavorite>>

}