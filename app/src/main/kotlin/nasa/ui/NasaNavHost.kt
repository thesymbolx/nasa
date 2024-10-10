package nasa.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.co.nasa.apod.APODRoute
import uk.co.nasa.apod.apodRoute


@Composable
fun NasaNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = APODRoute
    ) {
        apodRoute()

        composable<TAB2> {
            Text(text = "This is screen 2")
        }

        composable<TAB3> {
            Text(text = "This is screen 3")
        }
    }
}

@Serializable
object TAB2
@Serializable
object TAB3