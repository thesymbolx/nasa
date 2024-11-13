package uk.co.nasa.apod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import uk.co.nasa.ui.LoadingScreen
import uk.co.nasa.ui.parallaxLayoutModifier

@Composable
fun ApodScreen(viewModel: ApodViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val apodState = uiState.apodUiState

    //Overlay the loading screen on the content to allow preloading of the image to avoid jank.
    Box {
        if(apodState != null) ApodScreen(apodState, viewModel::contentLoaded)
        if(uiState.isLoading) LoadingScreen()
    }
}

@Composable
fun ApodScreen(
    uiState: ApodUiState,
    imageLoaded: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            0.92f to Color.Black,
                            1f to Color.Transparent
                        ), blendMode = BlendMode.DstIn
                    )
                }.parallaxLayoutModifier(scrollState, 2),
            model = uiState.imageUrl,
            onSuccess = {
                imageLoaded()
            },
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Header(title = uiState.title)

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
}

@Composable
private fun Header(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
        
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

//@Preview
//@Composable
//fun ApodScreenPreview() {
//    ApodScreen(
//        ApodUiState(
//            imageUrl = "https://apod.nasa.gov/apod/image/2207/",
//            title = "Some title",
//            description = "this is a description"
//        )
//    )
//}