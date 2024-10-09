package uk.co.nasa.apod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter


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
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            0.92f to Color.Black,
                            1f to Color.Transparent
                        ), blendMode = BlendMode.DstIn
                    )
                },
            model = uiState.imageUrl,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            text = uiState.title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 16.dp
            ),
            text = uiState.description,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Preview
@Composable
fun ApodScreenPreview() {
    ApodScreen(
        ApodUiState(
            imageUrl = "https://apod.nasa.gov/apod/image/2207/",
            title = "Some title",
            description = "this is a description"
        )
    )
}