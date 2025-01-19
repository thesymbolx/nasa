package uk.co.nasa.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NasaApp() {
    val navController = rememberNavController()
    val appState = rememberNiaAppState(navController)

    NasaAppBackground(modifier = Modifier) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NasaBottomNavigation(appState)
            },
            contentWindowInsets = WindowInsets.navigationBars
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                NasaNavHost(navController)
            }
        }
    }
}

@Composable
fun NasaAppBackground(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}

@Composable
fun NasaBottomNavigation(
    appState: NasaAppState
) {
    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified
                ),
                selected = appState.currentDestination?.hierarchy?.any {
                    it.hasRoute(topLevelRoute.route::class)
                } == true,
                onClick = { appState.navigateToTopLevelDestination(topLevelRoute) },
                icon = {
                    Icon(
                        imageVector = topLevelRoute.icon,
                        contentDescription = null
                    )
                },
            )
        }
    }
}