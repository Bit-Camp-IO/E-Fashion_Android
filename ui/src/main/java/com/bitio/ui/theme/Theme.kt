package com.bitio.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NeonBlue,
    secondary = NiceBlue,
    tertiary = PeacockBlue,
    background = Cinder,
    surface = BalticSea,
    onBackground = Color(0xFFAEAEAE)


)

private val LightColorScheme = lightColorScheme(
    primary = FireFly,
    secondary = NiceBlue,
    tertiary = PeacockBlue,
    background = Porcelain,
    onBackground = Color(0xFF2B1513),
    surface = Platinum,
    onSurface = Porcelain,

)

@Composable
fun EFashionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val typography = when {
        darkTheme -> AppTypography.getDarkThemeTypography()
        else -> AppTypography.getLightThemeTypography()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}