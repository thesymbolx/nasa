package uk.co.nasa.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.astronomyPictures.repository.AstronomyPicturesRepository
import uk.co.nasa.network.result.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val astronomyPicturesRepository: AstronomyPicturesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApodUiScreenState())
    val uiState = _uiState
        .onStart { getPictureOfTheDay() }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = ApodUiScreenState()
        )

    private fun getPictureOfTheDay() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        when (val result = astronomyPicturesRepository.getPictureOfTheDay()) {
            is NetworkResult.Error -> showError()
            is NetworkResult.Success -> showPictureOfTheDay(result.data)
        }
    }

    private fun showPictureOfTheDay(apod: APOD) {
        _uiState.update {
            it.copy(
                apodUiState = ApodUiState(
                    apod.url, apod.title, apod.description
                )
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