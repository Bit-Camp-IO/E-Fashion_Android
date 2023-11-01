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
    primary = Color(0xff99CBFF),
    onPrimary = Color(0xffF1F0F4),
    secondary = Color(0xff526070),
    tertiary = Color(0xff42474E),
    background = Color(0xff1A1C1E),
    onBackground = Color(0xffF1F0F4),
    surface = Color(0xff42474E),
    onSurface = Color(0xffC6C6C9),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xff00629D),
    onPrimary = Color(0xffF1F0F4),
    secondary = Color(0xff526070),
    tertiary = Color(0xff42474E),
    background = Color(0xffF1F0F4),
    onBackground = Color(0xff1A1C1E),
    surface = Color(0xffDEE3EB),
    onSurface = Color(0xff1A1C1E),
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