package uk.co.nasa.ui.mediaResources

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import uk.co.nasa.ui.modifiers.parallaxLayoutModifier

@Composable
fun ParallaxVideo(
    videoUrl: String,
    scrollState: ScrollState,
    videoLoaded: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember(context) { ExoPlayer.Builder(context).build() }
    val playerListener = object : Player.Listener {
        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            if (!isLoading) videoLoaded()
        }
    }

    DisposableEffect(videoUrl) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.release()
            exoPlayer.removeListener(playerListener)
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .parallaxLayoutModifier(scrollState, 2),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        }
    )
}