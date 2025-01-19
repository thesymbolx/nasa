package uk.co.nasa.historic_apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import uk.co.nasa.astronomyPictures.repository.ApodRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class HistoricApodViewModel @Inject constructor(
    private val apodRepository: ApodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoricApodUiState())
    val uiState = _uiState
        .onStart { apodRepository.getPicturesOfTheDay(
            startDate = LocalDate.of(2022, 1, 1)
        ) }
        .map {
            HistoricApodUiState(it.images)
        }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = HistoricApodUiState()
        )
}