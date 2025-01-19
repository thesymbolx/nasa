package uk.co.nasa.historic_apod

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class HistoricApodUiState (
    val images: ImmutableList<String> = persistentListOf()
)