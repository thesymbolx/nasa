package uk.co.nasa

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import uk.co.nasa.apod.ApodRoute
import uk.co.nasa.apod.apodDestination
import uk.co.nasa.favorite_images.favoriteApodDestination

@Composable
fun NasaNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ApodRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        apodDestination()
        favoriteApodDestination()
    }
}