package com.example.chronometron.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

// Define additional custom colors
val darkBlue = Color(0xFF03045E)
val customButtonColor = Color(0xFF00B4D8)
val accentColor = Color(0xFF34D2F5)
val errorColor = Color(0xFFB00020)
val secondColor = Color(0xFF0077B6)

// Extensive update to color schemes to ensure no defaults are causing grey colors
private val DarkColorScheme = darkColorScheme(
    primary = customButtonColor,
    onPrimary = Color.White,
    secondary = secondColor,
    onSecondary = Color.White,
    background = darkBlue,
    onBackground = Color.White,
    surface = darkBlue,
    onSurface = Color.White,
    error = errorColor,
    onError = Color.White,
    primaryContainer = darkBlue,
    onPrimaryContainer = Color.Black,
    inverseSurface = darkBlue,
    inverseOnSurface = Color.White,
    outline = Color.White,
    onSurfaceVariant = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = customButtonColor,
    onPrimary = Color.White,
    secondary = secondColor,
    onSecondary = Color.Black,
    background = darkBlue,
    onBackground = Color.White,
    surface = darkBlue,
    onSurface = Color.White,
    error = errorColor,
    onError = Color.White,
    primaryContainer = darkBlue,
    onPrimaryContainer = Color.Black,
    inverseSurface = darkBlue,
    inverseOnSurface = Color.White,
    outline = Color.White,
    onSurfaceVariant = Color.White
)

@Composable
fun ChronoMetronTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
