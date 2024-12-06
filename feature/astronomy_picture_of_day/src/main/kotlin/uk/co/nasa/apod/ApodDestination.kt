package uk.co.nasa.apod

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object APODRoute

fun NavGraphBuilder.apodDestination(
    imageSelected: (
        imageUrl: String,
        title: String,
        description: String
    ) -> Unit
) {
    composable<APODRoute> {
        ApodScreen(imageSelected = imageSelected)
    }
}