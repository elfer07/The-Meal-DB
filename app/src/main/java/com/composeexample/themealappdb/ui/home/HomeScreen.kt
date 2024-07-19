package com.composeexample.themealappdb.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composeexample.themealappdb.R
import com.composeexample.themealappdb.model.Meal
import com.composeexample.themealappdb.model.MealList
import com.composeexample.themealappdb.model.Route

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> MealScreen(meals = homeUiState.meals, homeViewModel = homeViewModel, modifier = modifier, navController = navController)
        is HomeUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(
    meals: MealList,
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var searchTerm by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate(Route.Favorite.route) }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = stringResource(
                            id = R.string.favorites
                        ))
                    }
                }
            )
        }
    ) { pd ->
        Column(
            modifier = modifier.padding(pd)
        ) {
            SearchBar(
                searchTerm = searchTerm,
                onSearchChange = {
                    searchTerm = it
                    homeViewModel.onSearchTermChange(it)
                },
                onSearch = { homeViewModel.onSearchTermChange(searchTerm) }
            )

            LazyColumn(modifier = modifier) {
                items(items = meals.meals) {
                    ItemMeal(meal = it) {
                        navController.navigate(Route.Detail.createRoute(it.idMeal))
                    }
                }
            }
        }
    }
}

@Composable
fun ItemMeal(meal: Meal, onClickMeal: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClickMeal()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(meal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.meal_photo),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
            )
            Text(
                text = meal.strMeal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SearchBar(searchTerm: String, onSearchChange: (String) -> Unit, onSearch: () -> Unit) {
    TextField(
        value = searchTerm,
        onValueChange = onSearchChange,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        placeholder = { Text(text = stringResource(id = R.string.placeholder_search_bar)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
