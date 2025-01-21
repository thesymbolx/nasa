package uk.co.nasa.favorite_images

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object FavoriteImagesRoute

fun NavGraphBuilder.favoriteImagesDestination() {
    composable<FavoriteImagesRoute> {
        FavoriteImagesScreen()
    }
}