package com.composeexample.themealappdb.ui.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composeexample.themealappdb.R
import com.composeexample.themealappdb.application.getIngredientsWithMeasures
import com.composeexample.themealappdb.application.goToWeb
import com.composeexample.themealappdb.model.MealList
import com.composeexample.themealappdb.model.toMealFavorite
import com.composeexample.themealappdb.ui.home.ErrorScreen
import com.composeexample.themealappdb.ui.home.LoadingScreen
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    navController: NavController,
    detailUiState: DetailUiState,
    detailViewModel: DetailViewModel,
    retryAction: () -> Unit,
    mealId: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(mealId) {
        detailViewModel.getMealDetail(mealId)
    }

    when (detailUiState) {
        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> MealDetailScreen(meal = detailUiState.meal, onBackClick = { navController.popBackStack() }, viewModel = detailViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(meal: MealList, onBackClick: () -> Unit, viewModel: DetailViewModel) {
    val mealItem = meal.meals[0]
    val ingredients = mealItem.getIngredientsWithMeasures()
    val context = LocalContext.current
    val isMealFavorite by viewModel.isMealFavorite.collectAsState()
    LaunchedEffect(mealItem) {
        viewModel.checkMealFavoriteStatus(mealItem.toMealFavorite())
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.meal_detail)) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.clickable {
                            onBackClick()
                        }
                    )
                }
            )
        }
    ) { pd ->
        LazyColumn(
            modifier = Modifier.padding(pd)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(mealItem.strMealThumb)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.meal_photo),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.ic_broken_image),
                            placeholder = painterResource(id = R.drawable.loading_img),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.saveOrDeleteFavoriteMeal(mealItem.toMealFavorite())
                                    val message = if (isMealFavorite) {
                                        context.getString(R.string.removed_from_favorites)
                                    } else {
                                        context.getString(R.string.added_to_favorites)
                                    }
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Image(
                                imageVector = if (isMealFavorite) Icons.Default.Delete else Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add)
                            )
                        }
                    }
                    Text(
                        text = mealItem.strMeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Divider()
                    Image(
                        painter = painterResource(id = R.drawable.youtube),
                        contentDescription = stringResource(id = R.string.youtube),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                goToWeb(mealItem.strYoutube, context)
                            }
                    )
                    Divider()
                    Text(
                        text = stringResource(id = R.string.ingredients),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            items(ingredients.entries.toList()) { (ingredient, measure) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$measure $ingredient")
                }
            }
            item {
                Divider()
                Text(
                    text = mealItem.strInstructions,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
