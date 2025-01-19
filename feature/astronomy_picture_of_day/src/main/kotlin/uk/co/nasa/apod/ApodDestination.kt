package uk.co.nasa.apod

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ApodRoute

fun NavGraphBuilder.apodDestination() {
    composable<ApodRoute> {
        ApodScreen()
    }
}