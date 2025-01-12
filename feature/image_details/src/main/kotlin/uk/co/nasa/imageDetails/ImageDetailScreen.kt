package uk.co.nasa.imageDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.co.nasa.ui.ShareHeader
import uk.co.nasa.ui.images.ParallaxImage

@Composable
fun ImageDetailsScreen(
    imageUrl: String,
    favorite: Boolean,
    title: String,
    description: String
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        ParallaxImage(
            imageUrl = imageUrl,
            scrollState = scrollState
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
                title = title,
                favoriteSelected = favorite,
                onFavoriteClick = { TODO() }
            )

            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 16.dp
                ),
                text = description,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}