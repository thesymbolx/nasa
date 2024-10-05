package uk.co.nasa.apod

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val apodRoute = "apod_route"

fun NavGraphBuilder.apodRoute() {
    composable(route = apodRoute) {
        ApodScreen()
    }
}