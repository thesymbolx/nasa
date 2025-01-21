package uk.co.nasa.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import uk.co.nasa.apod.ApodRoute
import uk.co.nasa.apod.apodDestination
import uk.co.nasa.favorite_images.favoriteImagesDestination
import uk.co.nasa.historic_apod.historicApodDestination

@Composable
fun NasaNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ApodRoute
    ) {
        apodDestination()
        historicApodDestination()
        favoriteImagesDestination()
    }
}