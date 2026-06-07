package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PharaonicGold,
    secondary = WestNileEmerald,
    tertiary = EastNileCrimson,
    background = ObsidianDarkBg,
    surface = DarkSurfaceCardBg,
    onPrimary = Color(0xFF0F172A), // Inside glowing gold headers/buttons, use dark-charcoal text
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    outline = DarkOutlineBorder
)

private val LightColorScheme = lightColorScheme(
    primary = PharaonicGoldDark, // Sophisticated warm golden-bronze with high contrast on white
    secondary = WestNileEmerald,
    tertiary = EastNileCrimson,
    background = PristineLightBg,
    surface = LightSurfaceCardBg,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    outline = LightOutlineBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
