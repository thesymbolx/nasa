package uk.co.nasa.favorite_images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import uk.co.nasa.astronomyPictures.repository.ApodFavoritesRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteApodViewModel @Inject constructor(
    private val apodFavoritesRepository: ApodFavoritesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteApodUIState())
    val uiState = _uiState
        .onStart { getFavorites() }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = FavoriteApodUIState()
        )

    private suspend fun getFavorites() {
        val favorites: ImmutableList<String> =
            apodFavoritesRepository.getFavorites()
                .map { it.imageUrl }
                .toPersistentList()

        _uiState.update {
            FavoriteApodUIState(favorites)
        }
    }
}