package uk.co.nasa.apod

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import uk.co.nasa.network.responseModel.MediaType

internal data class ApodUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val todayApod: ApodStateItem? = null,
    val historicApod: ImmutableList<ApodStateItem> = persistentListOf()
)

internal data class ApodStateItem(
    val imageUrl: String,
    val title: String,
    val description: String,
    val favorite: Boolean,
    val mediaType: MediaType
)