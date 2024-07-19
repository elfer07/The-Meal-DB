package com.composeexample.themealappdb.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeexample.themealappdb.data.local.LocalRepository
import com.composeexample.themealappdb.data.remote.MealRepository
import com.composeexample.themealappdb.model.MealFavorite
import com.composeexample.themealappdb.model.MealList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val localRepository: LocalRepository
): ViewModel() {
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _isMealFavorite = MutableStateFlow(false)
    val isMealFavorite: StateFlow<Boolean> = _isMealFavorite

    fun getMealDetail(idMeal: Int) {
        viewModelScope.launch {
            runCatching {
                mealRepository.getFullMealDetailsById(idMeal)
            }.onSuccess {
                detailUiState = DetailUiState.Success(it)
            }.onFailure {
                detailUiState = DetailUiState.Error
            }
        }
    }

    suspend fun saveOrDeleteFavoriteMeal(mealFavorite: MealFavorite) {
        viewModelScope.launch {
            if (localRepository.isMealFavorite(mealFavorite)) {
                localRepository.deleteMeal(mealFavorite)
                _isMealFavorite.value = false
            } else {
                localRepository.insertMeal(mealFavorite)
                _isMealFavorite.value = true
            }
        }
    }

    fun checkMealFavoriteStatus(mealFavorite: MealFavorite) {
        viewModelScope.launch {
            _isMealFavorite.value = localRepository.isMealFavorite(mealFavorite)
        }
    }
}

sealed interface DetailUiState {
    data class Success(val meal: MealList): DetailUiState
    object Error: DetailUiState
    object Loading: DetailUiState
}