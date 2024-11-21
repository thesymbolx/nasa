package uk.co.nasa.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import uk.co.nasa.ui.parallaxLayoutModifier

@Composable
fun ParallaxImage(
    imageUrl: String,
    scrollState: ScrollState,
    imageLoaded: () -> Unit
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .parallaxLayoutModifier(scrollState, 2)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        0.92f to Color.Black,
                        1f to Color.Transparent
                    ), blendMode = BlendMode.DstIn
                )
            },
        model = imageUrl,
        onSuccess = {
            imageLoaded()
        },
        error = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
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