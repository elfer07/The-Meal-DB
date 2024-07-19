package com.composeexample.themealappdb.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.composeexample.themealappdb.application.Constant.ID_MEAL
import com.composeexample.themealappdb.model.Route
import com.composeexample.themealappdb.ui.detail.DetailScreen
import com.composeexample.themealappdb.ui.detail.DetailViewModel
import com.composeexample.themealappdb.ui.favorite.FavoriteScreen
import com.composeexample.themealappdb.ui.favorite.FavoriteViewModel
import com.composeexample.themealappdb.ui.home.HomeScreen
import com.composeexample.themealappdb.ui.home.HomeViewModel

@Composable
fun MealApp() {
    Scaffold { pd ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
        ) {
            val navigationController = rememberNavController()
            val homeViewModel: HomeViewModel = viewModel()
            val detailViewModel: DetailViewModel = viewModel()
            val favoriteViewModel: FavoriteViewModel = viewModel()

            NavHost(navController = navigationController, startDestination = Route.Home.route) {
                composable(Route.Home.route) {
                    HomeScreen(
                        homeUiState = homeViewModel.homeUiState,
                        retryAction = { homeViewModel.retry() },
                        homeViewModel = homeViewModel,
                        navController = navigationController
                    )
                }

                composable(Route.Detail.route, arguments = listOf(navArgument(ID_MEAL) {
                    defaultValue = 0
                })) {
                    it.arguments?.getInt(ID_MEAL)?.let { it1 ->
                        DetailScreen(
                            navController = navigationController,
                            detailUiState = detailViewModel.detailUiState,
                            detailViewModel = detailViewModel,
                            retryAction = {},
                            mealId = it1
                        )
                    }
                }

                composable(Route.Favorite.route) {
                    FavoriteScreen(
                        navController = navigationController,
                        viewModel = favoriteViewModel
                    )
                }
            }
        }
    }
}