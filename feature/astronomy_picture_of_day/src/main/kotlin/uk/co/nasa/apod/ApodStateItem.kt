package uk.co.nasa.apod

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ApodUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val todayApod: ApodStateItem? = null,
    val historicApod: ImmutableList<ApodStateItem> = persistentListOf()
)

data class ApodStateItem(
    val imageUrl: String,
    val title: String,
    val description: String
)