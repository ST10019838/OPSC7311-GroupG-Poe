package com.example.chronometron.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)

val darkBlue = Color(0xFF03045E)
val customButtonColor = Color(0xFF00B4D8)
val accentColor = Color(0xFF34D2F5)
val errorColor = Color(0xFFB00020)
val secondColor = Color(0xFF0077B6)

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


//private val DarkColorScheme = darkColorScheme(
//    primary = teal_200,
//    secondary = teal_700,
//    tertiary = dark_blue
//)

//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

@Composable
fun ChronoMetronTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )

    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}