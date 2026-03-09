package com.jarvis.ai.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFba86fc),           // Roxo holográfico
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF3f2c70),
    onPrimaryContainer = Color(0xFFe8def8),
    secondary = Color(0xFF03dac6),         // Ciano holográfico
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF005047),
    onSecondaryContainer = Color(0xFF84f8f3),
    tertiary = Color(0xFF00BCD4),          // Ciano claro
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFF004d5c),
    onTertiaryContainer = Color(0xFF80f9ff),
    error = Color(0xFFFF5252),
    onError = Color(0xFF000000),
    errorContainer = Color(0xFF93000a),
    onErrorContainer = Color(0xFFffb4ab),
    background = Color(0xFF0a0e27),
    onBackground = Color(0xFFe6e1e6),
    surface = Color(0xFF1a1f3a),
    onSurface = Color(0xFFe6e1e6),
    surfaceVariant = Color(0xFF2a2f4a),
    onSurfaceVariant = Color(0xFFcac4d0),
    outline = Color(0xFF9590a0),
    outlineVariant = Color(0xFF4a4458),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFe6e1e6),
    inverseOnSurface = Color(0xFF1a1f3a),
    inversePrimary = Color(0xFF5a4080)
)

@Composable
fun JarvisTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = JarvisTypography,
        shapes = JarvisShapes,
        content = content
    )
}
