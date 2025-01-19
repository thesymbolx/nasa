package uk.co.nasa.historic_apod

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.SubcomposeAsyncImage
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HistoricApodScreen(
    historicApodViewModel: HistoricApodViewModel = hiltViewModel()
) {
    val uiState by historicApodViewModel.uiState.collectAsState()

    HistoricApodScreen(uiState.images)
}

@Composable
internal fun HistoricApodScreen(images: ImmutableList<String>) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = images, key = { it }) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}