package uk.co.nasa.ui.mediaResources

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import uk.co.nasa.ui.loading.TripleOrbitLoadingAnimation

@Composable
fun Video(
    videoUrl: String,
    modifier: Modifier = Modifier,
    videoLoaded: () -> Unit
) {
    val iframeHtml = """
        <!DOCTYPE html>
        <html>
        <head>
        <style>
            body {
                margin: 0;
                padding: 0;
                background-color: black;
                overflow: hidden;
            }
            iframe {
                display: block;
                border: none;
                width: 100vw;
                height: 100vh;
            }
        </style>
        </head>
        <body>
            <iframe 
                src="${videoUrl}?autoplay=0&controls=1"
                allow="autoplay; encrypted-media"
                allowfullscreen>
            </iframe>
        </body>
        </html>
    """

    var iFrameLoaded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = modifier,
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.javaScriptEnabled = true
                    webChromeClient = WebChromeClient()
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            iFrameLoaded = true
                        }
                    }
                    loadData(iframeHtml, "text/html", "UTF-8")
                }
            }
        )

        if (!iFrameLoaded) TripleOrbitLoadingAnimation(Modifier.size(80.dp))
    }

    SideEffect {
        videoLoaded()
    }
}