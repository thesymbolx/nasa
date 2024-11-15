package uk.co.nasa.apod

data class ApodUiScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val apodUiState: ApodUiState? = null
)

data class ApodUiState(
    val imageUrl: String,
    val title: String,
    val description: String
)