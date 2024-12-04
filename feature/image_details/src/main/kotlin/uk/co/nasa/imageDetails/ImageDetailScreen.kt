@file:OptIn(ExperimentalSharedTransitionApi::class)

package uk.co.nasa.imageDetails

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
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
fun SharedTransitionScope.ImageDetailsScreen(
    imageUrl: String,
    title: String,
    description: String,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        ParallaxImage(
            imageUrl = imageUrl,
            scrollState = scrollState,
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(imageUrl),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 1000)
                }
            )
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
                onShare = { TODO() },
                onBookmark = { TODO() }
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