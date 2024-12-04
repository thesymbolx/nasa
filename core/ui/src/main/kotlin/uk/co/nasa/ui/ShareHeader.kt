package uk.co.nasa.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShareHeader(
    title: String,
    onShare: () -> Unit,
    onBookmark: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        IconButton(onClick = onShare) {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp),
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = onBookmark) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}