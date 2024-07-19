package com.composeexample.themealappdb.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeexample.themealappdb.data.remote.MealRepository
import com.composeexample.themealappdb.model.MealList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMealsByName("Burger")
    }

    private fun getMealsByName(name: String) {
        viewModelScope.launch {
            runCatching {
                mealRepository.getMealsByName(name)
            }.onSuccess {
                homeUiState = HomeUiState.Success(it)
            }.onFailure {
                homeUiState = HomeUiState.Error
            }
        }
    }

    fun retry() {
        if (homeUiState is HomeUiState.Error) {
            getMealsByName("Burger")
        }
    }

    fun onSearchTermChange(searchTerm: String) {
        getMealsByName(searchTerm)
    }
}

sealed interface HomeUiState {
    data class Success(val meals: MealList): HomeUiState
    object Error: HomeUiState
    object Loading: HomeUiState
}