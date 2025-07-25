package uk.co.nasa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import uk.co.nasa.apod.ApodRoute
import uk.co.nasa.favorite_images.FavoriteImagesRoute
import uk.co.nasa.ui.R

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): NasaAppState {
    return remember(navController) {
        NasaAppState(
            navController = navController,
        )
    }
}

@Stable
class NasaAppState(
    private val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelRoute<out Any>) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination.route) {
            is ApodRoute -> navController.navigate(ApodRoute, topLevelNavOptions)
            is FavoriteImagesRoute -> navController.navigate(FavoriteImagesRoute, topLevelNavOptions)
        }
    }
}

data class TopLevelRoute<T : Any>(val route: T, val icon: Int)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = ApodRoute, icon = R.drawable.planet_2),
    TopLevelRoute(route = FavoriteImagesRoute, icon =  R.drawable.heart)
)