package uk.co.nasa.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val blue400 = Color(0xFF5aaadf)
val teal200 = Color(0xFF5adfd2)
val purple400 = Color(0xFF8f5adf)
val black900 = Color(0xFF212121)
val black800 = Color(0xFF202020)


val backgroundColor = Color.Black
val onBackgroundColor = Color.White
val primaryColor = blue400
val onPrimaryColor = Color.White
val secondaryColor = teal200
val onSecondaryColor = Color.White
val secondaryContainerColor = Color.Black
val onSecondaryContainerColor =  Color.White
val tertiaryColor = purple400
val onTertiaryColor = Color.White
val surfaceColor = black900
val onSurfaceColor = Color.White
val surfaceVariantColor = black800
val errorColor = Color.Red
val onErrorColor = Color.Red

internal val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    secondary = secondaryColor,
    onSecondary = onSecondaryColor,
    tertiary = tertiaryColor,
    onTertiary = onTertiaryColor,
    background = backgroundColor,
    onBackground = onBackgroundColor,
    secondaryContainer = secondaryContainerColor,
    onSecondaryContainer =  onSecondaryContainerColor,
    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = surfaceVariantColor,
    onError = onErrorColor,
    error = errorColor
)

internal val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    secondary = secondaryColor,
    onSecondary = onSecondaryColor,
    tertiary = tertiaryColor,
    onTertiary = onTertiaryColor,
    background = backgroundColor,
    onBackground = onBackgroundColor,
    secondaryContainer = secondaryContainerColor,
    onSecondaryContainer =  onSecondaryContainerColor,
    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = surfaceVariantColor,
    onError = onErrorColor,
    error = errorColor,
)