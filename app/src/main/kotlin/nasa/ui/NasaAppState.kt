package nasa.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import uk.co.nasa.apod.APODRoute

@Composable
fun rememberNiaAppState(
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
            is APODRoute -> navController.navigate(APODRoute, topLevelNavOptions)
            is TAB2 -> navController.navigate(TAB2, topLevelNavOptions)
            is TAB3 -> navController.navigate(TAB3, topLevelNavOptions)
        }
    }
}

data class TopLevelRoute<T : Any>(val route: T, val icon: ImageVector)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = APODRoute, icon = Icons.Default.Home),
    TopLevelRoute(route = TAB2, icon = Icons.Filled.Favorite),
    TopLevelRoute(route = TAB3, icon = Icons.Default.AccountCircle)
)