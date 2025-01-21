package uk.co.nasa.favorite_images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.nasa.ui.mediaResources.NasaAsyncImage

@Composable
fun FavoriteImagesScreen(favoriteImagesViewModel: FavoriteImagesViewModel = hiltViewModel()) {
    val uiState by favoriteImagesViewModel.uiState.collectAsState()
    FavoriteImagesScreen(uiState)
}

@Composable
fun FavoriteImagesScreen(favoriteUIState: FavoriteUIState) {
    val fullScreenImageUrl: String? by remember {
        mutableStateOf(null)
    }

    Box(contentAlignment = Alignment.Center) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = WindowInsets.statusBars.asPaddingValues(),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = favoriteUIState.imageUrls, key = { it }) { imageUrl ->
                NasaAsyncImage(imageUrl)
            }
        }

        if(fullScreenImageUrl != null) {
            NasaAsyncImage(imageUrl = fullScreenImageUrl!!)
        }

    }




}