package uk.co.nasa.apod

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object APODRoute

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.apodDestination(
    sharedTransitionScope: SharedTransitionScope,
    imageSelected: (
        imageUrl: String,
        title: String,
        description: String
    ) -> Unit
) {
    composable<APODRoute> {
        ApodScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this,
            imageSelected = imageSelected
        )
    }
}