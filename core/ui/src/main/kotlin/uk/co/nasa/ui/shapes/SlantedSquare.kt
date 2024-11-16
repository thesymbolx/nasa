package uk.co.nasa.ui.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class SlantedSquare : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f) // Top point
            lineTo(size.width / 1.25f, 0f) // Right point
            lineTo(size.width, size.height) // Bottom point
            lineTo(0f, size.height) // Left point
            close() // Close the path
        }

        return Outline.Generic(path)
    }
}