package uk.co.nasa.apod

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object APODRoute

fun NavGraphBuilder.apodDestination() {
    composable<APODRoute> {
        ApodScreen()
    }
}