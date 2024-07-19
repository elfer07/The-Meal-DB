package com.composeexample.themealappdb.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeexample.themealappdb.data.local.LocalRepository
import com.composeexample.themealappdb.model.MealFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    localRepository: LocalRepository
): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val favoriteUiState: StateFlow<FavoriteUiState> = localRepository.getAllMealsStream().map {
        FavoriteUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = FavoriteUiState()
    )
}

data class FavoriteUiState(val favorites: List<MealFavorite> = listOf())