package uk.co.nasa.historic_apod

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HistoricApodRoute

fun NavGraphBuilder.historicApodDestination() {
    composable<HistoricApodRoute> {
        HistoricApodScreen()
    }
}