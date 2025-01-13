package uk.co.nasa.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.astronomyPictures.repository.ApodFavoritesRepository
import uk.co.nasa.astronomyPictures.repository.ApodRepository
import uk.co.nasa.network.result.NetworkResult
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val apodRepository: ApodRepository,
    private val apodFavoritesRepository: ApodFavoritesRepository
) : ViewModel() {
    private var apods = emptyList<APOD>()

    private val _uiState = MutableStateFlow(ApodUiState())
    val uiState = _uiState
        .onStart { getPictureOfTheDay() }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = ApodUiState()
        )

    private fun getPictureOfTheDay() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        val today = LocalDate.now()
        val minus10Days = today.minusDays(10)

        when (val result = apodRepository.getPicturesOfTheDay(minus10Days, today)) {
            is NetworkResult.Error -> showError()
            is NetworkResult.Success -> {
                apods = result.data
                showPictureOfTheDay(apods)
            }
        }
    }

    fun apodSelected(currentApodImageUrl: String) = showPictureOfTheDay(apods, currentApodImageUrl)

    private fun showPictureOfTheDay(apods: List<APOD>, currentApodImageUrl: String? = null) {
        val currentApod = apods.firstOrNull { it.url == currentApodImageUrl } ?: apods.lastOrNull()

        currentApod ?: run {
            _uiState.update {
                it.copy(isError = true, isLoading = false)
            }
            return
        }

        val apodNewestFirst = apods
            .reversed()
            .filter { it.url != currentApod.url }

        _uiState.update {
            it.copy(
                todayApod = ApodStateItem(
                    imageUrl = currentApod.url,
                    title = currentApod.title,
                    description = currentApod.description,
                    favorite = currentApod.favorite
                ),
                historicApod = apodNewestFirst.map { historicApod ->
                    ApodStateItem(
                        imageUrl = historicApod.url,
                        title = historicApod.title,
                        description = historicApod.description,
                        favorite = historicApod.favorite
                    )
                }.toImmutableList()
            )
        }
    }

    fun saveFavorite(imageUrl: String, isSelected: Boolean) = viewModelScope.launch {
        if (isSelected)
            apodFavoritesRepository.saveFavorites(imageUrl)
        else
            apodFavoritesRepository.removeFavorite(imageUrl)


        _uiState.update {
            it.copy(
                todayApod = it.todayApod?.copy(favorite = isSelected)
            )
        }
    }

    private fun showError() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    fun contentLoaded() {
        _uiState.update {
            it.copy(isLoading = false)
        }
    }
}