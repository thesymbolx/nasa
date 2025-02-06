package uk.co.nasa.apod

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import uk.co.nasa.designsystem.theme.NasaTheme
import uk.co.nasa.network.responseModel.MediaType
import uk.co.nasa.ui.ErrorScreen
import uk.co.nasa.ui.loading.LoadingScreen
import uk.co.nasa.ui.mediaResources.ParallaxImage
import uk.co.nasa.ui.mediaResources.ParallaxVideo
import uk.co.nasa.ui.mediaResources.YouTubeVideo

@Composable
internal fun ApodScreen(viewModel: ApodViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val todayApod = uiState.todayApod

    //Overlay the loading screen on the content to allow preloading of the image to avoid jank.
    Box {
        when {
            todayApod != null -> ApodScreen(
                todayApod = todayApod,
                historicApod = uiState.historicApod,
                imageLoaded = viewModel::contentLoaded,
                imageSelected = viewModel::apodSelected,
                onFavoriteClick = viewModel::saveFavorite
            )

            uiState.isError -> ErrorScreen()
        }
        if (uiState.isLoading) LoadingScreen()
    }
}

@Composable
internal fun ApodScreen(
    todayApod: ApodStateItem,
    historicApod: ImmutableList<ApodStateItem>,
    imageLoaded: () -> Unit,
    imageSelected: (imageUrl: String) -> Unit,
    onFavoriteClick: (imageUrl: String, isSelected: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(Modifier.verticalScroll(scrollState)) {

        ApodHeader(
            apodUrl = todayApod.apodUrl,
            mediaType = todayApod.mediaType,
            scrollState = scrollState,
            apodLoaded = {
                scope.launch {
                    scrollState.animateScrollTo(0)
                    imageLoaded()
                }
            }
        )

        ApodBody(
            todayApod = todayApod,
            historicApod = historicApod,
            imageSelected = imageSelected,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Composable
private fun ApodHeader(
    apodUrl: String,
    mediaType: MediaType,
    scrollState: ScrollState,
    apodLoaded: () -> Unit
) {
    if (mediaType == MediaType.IMAGE) {
        ParallaxImage(
            imageUrl = apodUrl,
            scrollState = scrollState,
            imageLoaded = apodLoaded
        )
    } else {
        ParallaxVideo(
            videoUrl = apodUrl,
            scrollState = scrollState,
            videoLoaded = apodLoaded
        )
    }
}

@Composable
private fun ApodBody(
    todayApod: ApodStateItem,
    historicApod: ImmutableList<ApodStateItem>,
    imageSelected: (imageUrl: String) -> Unit,
    onFavoriteClick: (imageUrl: String, isSelected: Boolean) -> Unit
) {
    Column(
        modifier = Modifier.drawBehind {
            val gradientBrush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = 0f,
                endY = size.height * 0.1f
            )
            drawRect(brush = gradientBrush)
        }
    ) {
        ShareHeader(
            title = todayApod.title,
            favoriteSelected = todayApod.favorite,
            favoriteVisible = todayApod.mediaType == MediaType.IMAGE,
            onFavoriteClick = { isSelected ->
                onFavoriteClick(todayApod.apodUrl, isSelected)
            }
        )

        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 16.dp
            ),
            text = todayApod.description,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        historicApod.forEach { item ->
            HistoricApod(
                apodUrl = item.apodUrl,
                title = item.title,
                isImage = item.mediaType == MediaType.IMAGE,
                apodSelected = imageSelected
            )
        }
    }
}

@Composable
private fun HistoricApod(
    apodUrl: String,
    title: String,
    isImage: Boolean,
    apodSelected: (imageUrl: String) -> Unit
) {
    Card(
        modifier = Modifier.padding(
            vertical = 10.dp,
            horizontal = 16.dp
        )
    ) {
        Row(
            modifier = Modifier
                .height(90.dp)
                .clickable {
                    apodSelected(apodUrl)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isImage) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.33f),
                    model = apodUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.33f)
                        .background(Color.Red),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.padding(24.dp),
                    )
                }
            }

            Text(
                modifier = Modifier
                    .weight(0.66f)
                    .padding(16.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ShareHeader(
    title: String,
    favoriteVisible: Boolean = true,
    favoriteSelected: Boolean,
    onFavoriteClick: (isSelected: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f)
                .padding(end = 8.dp),
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        if (favoriteVisible) {
            IconButton(
                modifier = Modifier
                    .padding(end = 4.dp),
                onClick = {
                    onFavoriteClick(!favoriteSelected)
                }
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = if (favoriteSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ApodScreenPreview() {
    NasaTheme {
        ApodScreen(
            todayApod = ApodStateItem(
                apodUrl = "",
                title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean velit metus, cursus a tellus quis, tincidunt tempor leo. Curabitur sodales pretium elit, in facilisis odio dignissim quis. Praesent consectetur in neque ut gravida. Quisque ac tristique nibh, suscipit vestibulum lacus. Vivamus porta eu felis non ornare. Vivamus eget ultrices est.",
                favorite = true,
                mediaType = MediaType.IMAGE
            ),
            historicApod = persistentListOf(
                ApodStateItem(
                    apodUrl = "",
                    title = "Lorem ipsum dolor sit amet, consectetur.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque id libero consectetur, rhoncus velit sed, commodo augue. Mauris quam mi.",
                    favorite = true,
                    mediaType = MediaType.IMAGE
                ),
                ApodStateItem(
                    apodUrl = "",
                    title = "Lorem ipsum dolor sit amet, consectetur.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque id libero consectetur, rhoncus velit sed, commodo augue. Mauris quam mi.",
                    favorite = true,
                    mediaType = MediaType.IMAGE
                ),
                ApodStateItem(
                    apodUrl = "",
                    title = "Lorem ipsum dolor sit amet, consectetur.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque id libero consectetur, rhoncus velit sed, commodo augue. Mauris quam mi.",
                    favorite = true,
                    mediaType = MediaType.IMAGE
                ),
                ApodStateItem(
                    apodUrl = "",
                    title = "Lorem ipsum dolor sit amet, consectetur.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque id libero consectetur, rhoncus velit sed, commodo augue. Mauris quam mi.",
                    favorite = true,
                    mediaType = MediaType.IMAGE
                )
            ),
            imageLoaded = {},
            imageSelected = {},
            onFavoriteClick = { _, _ -> }
        )
    }
}