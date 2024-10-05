package uk.co.nasa.apod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.material3.Text


@Composable
fun ApodScreen(viewModel: ApodViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        ApodUiScreenState.Loading -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )

        is ApodUiScreenState.Success -> ApodScreen(state.apodUiState)
    }
}

@Composable
fun ApodScreen(uiState: ApodUiState) {
    Column {
        AsyncImage(
            model = uiState.imageUrl,
            contentDescription = null
        )

        Text(text = uiState.description)

    }


}