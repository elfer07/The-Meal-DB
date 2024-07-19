package com.composeexample.themealappdb.model

sealed class Route(
    val route: String
) {
    object Home: Route("home")
    object Detail: Route("detail?idMeal={idMeal}") {
        fun createRoute(idMeal: Int) = "detail?idMeal=$idMeal"
    }
    object Favorite: Route("favorite")
}