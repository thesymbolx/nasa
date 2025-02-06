@file:OptIn(ExperimentalSharedTransitionApi::class)

package uk.co.nasa.favorite_images

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList
import uk.co.nasa.ui.mediaResources.NasaAsyncImage

@Composable
fun FavoriteApodScreen(favoriteApodViewModel: FavoriteApodViewModel = hiltViewModel()) {
    val uiState by favoriteApodViewModel.uiState.collectAsState()
    FavoriteApodScreen(uiState)
}

@Composable
fun FavoriteApodScreen(favoriteApodUIState: FavoriteApodUIState) {
    val gridState = LazyStaggeredGridState()

    var showGallery: Boolean by remember {
        mutableStateOf(true)
    }

    var fullScreenImageUrl: String by remember {
        mutableStateOf("")
    }

    SharedTransitionLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = showGallery,
            label = "image_transition"
        ) { targetState ->
            if (targetState) {
                Thumbnails(
                    favoriteApodUIState.imageUrls,
                    animatedVisibilityScope = this@AnimatedContent,
                    gridState
                ) { imageUrl ->
                    fullScreenImageUrl = imageUrl
                    showGallery = false
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NasaAsyncImage(
                        imageUrl = fullScreenImageUrl,
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = fullScreenImageUrl),
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    ) {
                        showGallery = true
                    }
                }
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.Thumbnails(
    imageUrls: ImmutableList<String>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: LazyStaggeredGridState,
    imageClicked: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        verticalItemSpacing = 4.dp,
        state = state,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = imageUrls, key = { it }) { imageUrl ->
            NasaAsyncImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = imageUrl),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                imageClicked(imageUrl)
            }
        }
    }
}