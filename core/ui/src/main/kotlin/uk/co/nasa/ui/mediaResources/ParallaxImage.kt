package uk.co.nasa.ui.mediaResources

import android.R
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.DrawableImage
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.SubcomposeAsyncImage
import coil3.test.FakeImage
import uk.co.nasa.ui.modifiers.parallaxLayoutModifier


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ParallaxImage(
    imageUrl: String,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    imageLoaded: () -> Unit = {}
) {
    val previewHandler = AsyncImagePreviewHandler {
        FakeImage(
            width = 100,
            height = 100,
            size = 50,
            shareable = false,
            color = Color.DarkGray.toArgb()
        )
    }

    val imageAlpha by remember {
        derivedStateOf {
            val maxValue = scrollState.maxValue.toFloat()
            val scroll = scrollState.value.toFloat()
            val scrollMultiplier = 3
            val alpha = 1f - (scroll * scrollMultiplier / maxValue)
            minOf(alpha, 1f)
        }
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .parallaxLayoutModifier(scrollState, 2)
                .alpha(imageAlpha)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            0.92f to Color.Black,
                            1f to Color.Transparent
                        ), blendMode = BlendMode.DstIn
                    )
                }
                .then(modifier),
            model = imageUrl,
            onSuccess = {
                imageLoaded()
            },
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