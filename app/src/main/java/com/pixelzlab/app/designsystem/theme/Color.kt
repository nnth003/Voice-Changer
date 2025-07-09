package com.pixelzlab.app.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light Theme Colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

// Dark Theme Colors
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

/**
 * Legacy Colors object for backward compatibility
 * New code should use MaterialTheme.colorScheme or MaterialTheme.customColors
 */
object Colors {
    val Primary = Purple40
    val Secondary = PurpleGrey40
    val Tertiary = Pink40
    val TextPrimary = Color(0xFF1C1B1F)
    val TextSecondary = Color(0xFF625b71)
    val Background = Color(0xFFFFFBFE)
    val Surface = Color(0xFFFFFBFE)
    val Warning = Color(0xFFFFA726)
    val Success = Color(0xFF66BB6A)
    val Info = Color(0xFF29B6F6)
}

// Material 3 Color Schemes
val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
)

// Custom Colors
val LightCustomColors = CustomColorsPalette(
    warning = Color(0xFFFFA726),
    success = Color(0xFF66BB6A),
    info = Color(0xFF29B6F6),
    divider = Color(0xFFE0E0E0),
    uiBorder = Color(0xFFE0E0E0),
    placeholder = Color(0xFF9E9E9E)
)

val DarkCustomColors = CustomColorsPalette(
    warning = Color(0xFFFFB74D),
    success = Color(0xFF81C784),
    info = Color(0xFF4FC3F7),
    divider = Color(0xFF424242),
    uiBorder = Color(0xFF424242),
    placeholder = Color(0xFFBDBDBD)
)