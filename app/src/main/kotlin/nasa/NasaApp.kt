package nasa

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import uk.co.nasa.apod.ApodScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NasaApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NasaAppBackground(Modifier.padding()) {
            ApodScreen()
        }
    }
}

@Composable
fun NasaAppBackground(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}