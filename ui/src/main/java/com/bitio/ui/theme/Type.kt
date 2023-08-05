package com.bitio.ui.theme

import androidx.compose.material3.Typography
import com.bitio.ui.theme.textStyles.AppThemeTextStyles


object AppTypography {
    private val darkThemeFontColor = LightGrey
    private val lightThemeColor = DarkGrey
    private val darkThemeTextStyles by lazy { AppThemeTextStyles(darkThemeFontColor) }
    private val lightThemeTextStyles by lazy { AppThemeTextStyles(lightThemeColor) }
    fun getLightThemeTypography(): Typography {
        return Typography(
            displayLarge = lightThemeTextStyles.displayLarge,
            titleLarge = lightThemeTextStyles.titleLarge,
            titleMedium = lightThemeTextStyles.titleMedium,
            titleSmall = lightThemeTextStyles.titleSmall,
            bodyLarge = lightThemeTextStyles.bodyLarge,
            bodyMedium = lightThemeTextStyles.bodyMedium,
            bodySmall = lightThemeTextStyles.bodySmall,
        )
    }

    fun getDarkThemeTypography(): Typography {
        return Typography(
            displayLarge = darkThemeTextStyles.displayLarge,
            titleLarge = darkThemeTextStyles.titleLarge,
            titleMedium = darkThemeTextStyles.titleMedium,
            titleSmall = darkThemeTextStyles.titleSmall,
            bodyLarge = darkThemeTextStyles.bodyLarge,
            bodyMedium = darkThemeTextStyles.bodyMedium,
            bodySmall = darkThemeTextStyles.bodySmall,
        )
    }

}
