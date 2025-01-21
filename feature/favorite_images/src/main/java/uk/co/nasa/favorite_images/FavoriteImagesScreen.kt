package uk.co.nasa.favorite_images

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.SubcomposeAsyncImage
import uk.co.nasa.ui.mediaResources.ParallaxImage
import uk.co.nasa.ui.modifiers.parallaxLayoutModifier

@Composable
fun FavoriteImagesScreen(favoriteImagesViewModel: FavoriteImagesViewModel = hiltViewModel()) {
    val uiState by favoriteImagesViewModel.uiState.collectAsState()
    FavoriteImagesScreen(uiState)
}

@Composable
fun FavoriteImagesScreen(favoriteUIState: FavoriteUIState) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
      verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(items = favoriteUIState.imageUrls, key = { it }) { imageUrl ->
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                  //  .border(1.dp, MaterialTheme.colorScheme.primary),
                model = imageUrl,
                error = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Failed to load image",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}