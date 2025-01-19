package uk.co.nasa.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.co.nasa.apod.ApodRoute
import uk.co.nasa.apod.apodDestination
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

        composable<TAB3> {
            Text(text = "This is screen 3")
        }
    }
}

@Serializable
object TAB3