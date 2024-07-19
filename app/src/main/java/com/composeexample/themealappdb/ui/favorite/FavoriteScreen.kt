package com.composeexample.themealappdb.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composeexample.themealappdb.R
import com.composeexample.themealappdb.model.Route
import com.composeexample.themealappdb.model.toMeal
import com.composeexample.themealappdb.ui.home.ItemMeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    val favoriteUiState by viewModel.favoriteUiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.favorites)) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    ) { pd ->
        Box(
            modifier = Modifier
                .padding(pd)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (favoriteUiState.favorites.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.without_data),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = favoriteUiState.favorites) {
                        ItemMeal(meal = it.toMeal()) {
                            navController.navigate(Route.Detail.createRoute(it.idMeal))
                        }
                    }
                }
            }
        }
    }
}