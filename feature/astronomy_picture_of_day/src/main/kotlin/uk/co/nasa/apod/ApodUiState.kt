package uk.co.nasa.apod

data class ApodUiState(
    val imageUrl: String,
    val description: String
)

sealed interface ApodUiScreenState {
    data object Loading : ApodUiScreenState

    data class Success(
        val apodUiState: ApodUiState,
    ) : ApodUiScreenState
}