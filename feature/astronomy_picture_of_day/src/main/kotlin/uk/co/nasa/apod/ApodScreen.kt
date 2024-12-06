package uk.co.nasa.apod

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerControlView
import coil3.compose.SubcomposeAsyncImage
import kotlinx.collections.immutable.ImmutableList
import uk.co.nasa.ui.ErrorScreen
import uk.co.nasa.ui.LoadingScreen
import uk.co.nasa.ui.ShareHeader
import uk.co.nasa.ui.images.ParallaxImage
import uk.co.nasa.ui.shapes.SlantedSquare

@Composable
fun ApodScreen(
    viewModel: ApodViewModel = hiltViewModel(),
    imageSelected: (
        imageUrl: String,
        title: String,
        description: String
    ) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val todayApod = uiState.todayApod

    //Overlay the loading screen on the content to allow preloading of the image to avoid jank.
    Box {
        when {
            todayApod != null -> ApodScreen(
                todayApod = todayApod,
                historicApod = uiState.historicApod,
                imageLoaded = viewModel::contentLoaded,
                imageSelected = imageSelected
            )

            uiState.isError -> ErrorScreen()
        }
        if (uiState.isLoading) LoadingScreen()
    }
}

@Composable
fun ApodScreen(
    todayApod: ApodStateItem,
    historicApod: ImmutableList<ApodStateItem>,
    imageLoaded: () -> Unit,
    imageSelected: (
        imageUrl: String,
        title: String,
        description: String
    ) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        ParallaxImage(
            imageUrl = todayApod.imageUrl,
            scrollState = scrollState,
            imageLoaded = imageLoaded,
        )

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
                onBookmark = { TODO() }
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
                    imageUrl = item.imageUrl,
                    title = item.title,
                    imageSelected = {
                        imageSelected(
                            item.imageUrl,
                            item.title,
                            item.description
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoricApod(
    imageUrl: String,
    title: String,
    imageSelected: () -> Unit
) {
    Card(
        modifier = Modifier.padding(
            vertical = 12.dp,
            horizontal = 16.dp
        )
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .clickable {
                    imageSelected()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.33f),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .weight(0.66f)
                    .padding(16.dp),
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp
                )
            )
        }
    }
}