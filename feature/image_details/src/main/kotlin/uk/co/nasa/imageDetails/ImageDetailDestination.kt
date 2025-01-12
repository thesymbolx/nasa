package uk.co.nasa.imageDetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ImageDetailRoute(
    val imageUrl: String,
    val title: String,
    val description: String,
    val favorite: Boolean
)

fun NavController.navigateToImageDetails(
    imageUrl: String,
    description: String,
    title: String,
    favorite: Boolean
) = navigate(ImageDetailRoute(imageUrl, title, description, favorite))

fun NavGraphBuilder.imageDetailDestination() {
    composable<ImageDetailRoute> { navBackStackEntry ->
        val route = navBackStackEntry.toRoute<ImageDetailRoute>()

        ImageDetailsScreen(
            imageUrl = route.imageUrl,
            description = route.description,
            favorite = route.favorite,
            title = route.title,
        )
    }
}